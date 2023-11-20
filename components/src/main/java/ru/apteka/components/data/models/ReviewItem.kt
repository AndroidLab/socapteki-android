package ru.apteka.components.data.models


/**
 * Представляет модель отзыва.
 */
data class ReviewItem(
    val name: String,
    val rating: Float,
    val date: Int,
    val text: String
)