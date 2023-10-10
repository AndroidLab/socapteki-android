package ru.apteka.common.data.services.message_notice_service.models

import ru.apteka.resources.R


/**
 * Описывает уровни важности сообщений.
 */
enum class MessageLevel(var color: Int) {

    /**
     * Уровень сообщения для показа информации.
     */
    USUAL(R.color.white),
    /**
     * Уровень сообщения для показа информации.
     */
    INFO(R.color.light_green),

    /**
     * Уровень сообщения для показа предупреждения.
     */
    WARNING(R.color.light_orange),

    /**
     * Уровень сообщения для показа ошибки.
     */
    ERROR(R.color.light_red)

}