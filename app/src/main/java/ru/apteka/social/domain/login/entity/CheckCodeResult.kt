package ru.apteka.social.domain.login.entity


/**
 * Представляет результат проверки кода.
 * @param success Флаг успеха.
 */
data class CheckCodeResult(
    val success: Boolean
)
