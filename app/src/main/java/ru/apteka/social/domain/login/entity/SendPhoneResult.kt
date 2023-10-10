package ru.apteka.social.domain.login.entity


/**
 * Представляет результат запроса кода по номеру телефона.
 * @param success Флаг успеха.
 */
data class SendPhoneResult(
    val success: Boolean
)
