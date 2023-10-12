package ru.apteka.home.data.models


/**
 * Представляет модель скидки.
 * @param oldPrice Скидка.
 * @param percent Процент.
 */
data class DiscountModel(
    val oldPrice: String,
    val percent: String
)
