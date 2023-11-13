package ru.apteka.components.data.services.account.models

import androidx.annotation.Keep


/**
 * Представляет аккаунт.
 * @param phoneNumber Телефонный номер.
 * @param token Токен аккаунта.
 */
@Keep
data class Account(
    val phoneNumber: String?,
    val token: String?,

)