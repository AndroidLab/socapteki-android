package ru.apteka.components.data.utils.event_dispatcher.impl

import ru.apteka.components.data.utils.event_dispatcher.api.IEventDispatcher
import ru.apteka.components.data.utils.event_dispatcher.api.Subscriber
import ru.apteka.components.data.utils.event_dispatcher.models.Event

val eventStorage = mutableMapOf<String, MutableList<Any?>>()

/**
 * Представляет хранилище событий.
 */
object EventDispatcher : IEventDispatcher {
    override val subscribers = mutableMapOf<String, MutableList<Subscriber<Any>>>()
    override val priorityListeners = mutableMapOf<Subscriber<Any>, Int?>()
}

/**
 * Подписывает обработчик событий к заданному имени события.
 * @param eventName Имя события для подписки.
 * @param subscriber Функция обработки событий.
 * @param priority Приоритет вызова обработчика событий.
 */
inline fun <reified T : Any> IEventDispatcher.subscribe(
    eventName: String,
    noinline subscriber: Subscriber<T>,
    priority: Int? = null
) {
    val subs = subscribers
    val ls = subs.getOrPut(eventName) { mutableListOf() }
    if (ls.indexOf(subscriber as Subscriber<*>) < 0) {
        ls.add(subscriber)
        priority?.takeIf { it > 0 }?.let {
            priorityListeners[subscriber] = priority
            ls.sortBy { subscriber -> priorityListeners.getOrPut(subscriber) { priority } }
        }
    }
    if (eventStorage.containsKey(eventName)) {
        eventStorage[eventName]?.forEach {
            call(eventName, it)
        }
    }
}

/**
 * Отписывает обработчик событий от заданного имени события.
 * @param eventName Имя события.
 * @param subscriber Функция обработки событий.
 */
inline fun <reified T : Any> IEventDispatcher.unsubscribe(
    eventName: String,
    noinline subscriber: Subscriber<T>? = null
) {
    (subscriber as? Subscriber<Any>)?.let { sub ->
        val localSubscribers = subscribers
        val localPriorityListener = priorityListeners
        localPriorityListener.remove(sub)
        val ls = localSubscribers[eventName]
        ls?.let { listeners ->
            if (listeners.remove(sub)) {
                if (listeners.isEmpty()) {
                    localSubscribers.remove(eventName)?.clear()
                }
            }
        }
    } ?: unsubscribeAll(eventName)
}

/**
 * Отписывает от всех слушателей событий.
 * @param eventName Имя события.
 */
fun IEventDispatcher.unsubscribeAll(eventName: String) {
    if (hasSubscribers(eventName)) {
        val localSubscribers = subscribers
        localSubscribers.remove(eventName)?.clear()
    }
}

/**
 * Вызывает событие.
 * @param eventName Имя события.
 * @param data Данные для отправки.
 */
fun IEventDispatcher.call(eventName: String, data: Any? = null) {
    val localSubscribers = subscribers.toMap()
    val ls = localSubscribers[eventName]?.toList()
    ls?.let {
        val event = Event(data, eventName)
        it.forEach {
            it(event)
            if (eventStorage.containsKey(eventName)) {
                eventStorage.remove(eventName)
            }
        }
        event.data = null
    } ?: run {
        if (!eventStorage.containsKey(eventName)) {
            eventStorage[eventName] = mutableListOf()
        }
        eventStorage[eventName]?.add(data)
    }
}

/**
 * Проверяет, есть ли у данного имени события какие-либо подписчики.
 * @param eventName Имя события.
 */
fun IEventDispatcher.hasSubscribers(eventName: String): Boolean {
    val localSubscribers = subscribers.toMap()
    return localSubscribers[eventName]?.isNotEmpty() == true
}