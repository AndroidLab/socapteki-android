package ru.apteka.components.data.models


/**
 * Представляет модель скидки.
 * @param oldPrice Скидка.
 * @param percent Процент.
 */
data class DiscountModel(
    val oldPrice: String,
    val percent: String
)
