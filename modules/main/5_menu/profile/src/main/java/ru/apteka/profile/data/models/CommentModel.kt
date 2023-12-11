package ru.apteka.profile.data.models

import androidx.annotation.StringRes
import ru.apteka.profile.R

/**
 *
 */
data class CommentModel(
    val image: String,
    val status: CommentStatus,
    val text: String,
    val answers: List<AnswerModel>
) {
    enum class CommentStatus(@StringRes val text: Int) {
        Published(R.string.comments_reviews_published),
        Consideration(R.string.comments_reviews_consideration)
    }

    data class AnswerModel(
        val text: String
    )
}
