package ru.apteka.profile.data.models

import java.util.UUID

/**
 * Представляет модель обратной связи.
 */
data class FeedbackModel(
    val title: String,
    val text: String,
    val id: UUID = UUID.randomUUID()
)
