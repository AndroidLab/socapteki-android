package ru.apteka.components.data.services.account.models

import androidx.annotation.Keep


/**
 * Представляет аккаунт.
 * @param phoneNumber елефонный номер.
 * @param userId Идентификатор полдьзователя.
 * @param token Токен аккаунта.
 */
@Keep
data class Account(
    val phoneNumber: String?,
    val userId: String?,
    val token: String?
)