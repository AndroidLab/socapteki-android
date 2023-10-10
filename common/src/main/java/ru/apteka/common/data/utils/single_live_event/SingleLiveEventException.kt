package ru.apteka.common.data.utils.single_live_event

/**
 * Представляет класс исключения для [SingleLiveEvent].
 */
class SingleLiveEventException : Exception("Multiple observers registered but only one will be notified of changes.")