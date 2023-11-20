package ru.apteka.product_card.data.model

import ru.apteka.components.data.models.ReviewItem


/**
 * Comment model
 */
data class CommentModel(
    val rating: Float,
    val commentCount: Int,
    val comments: List<ReviewItem>
)
