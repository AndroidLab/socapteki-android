package ru.apteka.home.data.models


/**
 * Представляет модель заглушку бонуса для не зарегистрированного пользователя.
 */
data class BonusStubModel(
    val titleLength: Int,
    val descLength: Int,
    val isProfit: Boolean
)
