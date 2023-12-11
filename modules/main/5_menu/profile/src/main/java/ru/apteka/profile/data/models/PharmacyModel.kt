package ru.apteka.profile.data.models

import java.util.UUID


/**
 * Представляет модель аптеки.
 */
data class PharmacyModel(
    val uuid: UUID,
    val image: String,
    val title: String,
    val desc: String,
    val number: String,
    val isFavorite: Boolean
)