package ru.apteka.home.data.models

import java.util.UUID


/**
 * Представляет модель для карточки остальное.
 */
data class OtherModel(
    val id: UUID,
    val title: String,
    val description: String,
    val image: String
)