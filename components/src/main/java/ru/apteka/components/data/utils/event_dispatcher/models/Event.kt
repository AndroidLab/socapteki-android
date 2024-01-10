package ru.apteka.components.data.utils.event_dispatcher.models

/**
 * Event dispatcher.
 * @param data Данные для отправки.
 * @param eventName Имя события.
 */
data class Event<T : Any?>(var data: T? = null, var eventName: String)
