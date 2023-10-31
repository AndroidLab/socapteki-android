package ru.apteka.product_card.data.model


/**
 * Comment model
 */
data class CommentModel(
    val rating: Float,
    val commentCount: Int,
    val comments: List<CommentItem>
) {
    data class CommentItem(
        val name: String,
        val rating: Float,
        val date: Int,
        val text: String
    )
}
