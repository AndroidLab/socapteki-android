package ru.apteka.components.data.utils.event_dispatcher.api

import ru.apteka.components.data.utils.event_dispatcher.models.Event

/**
 * Псевдоним для лямбды подписчика.
 */
typealias Subscriber<T> = (Event<T>) -> Unit

/**
 * Описывает свойства событий.
 */
interface IEventDispatcher {
    /**
     * Возвращает карту имени события и слушателя обратного вызова.
     */
    val subscribers: MutableMap<String, MutableList<Subscriber<Any>>>

    /**
     * Возвращает приоритет события.
     */
    val priorityListeners: MutableMap<Subscriber<Any>, Int?>
}
