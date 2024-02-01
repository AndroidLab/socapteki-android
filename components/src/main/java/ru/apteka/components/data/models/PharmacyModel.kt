package ru.apteka.components.data.models

import java.util.UUID

/**
 * Представляет модель аптеки.
 */
data class PharmacyModel(
    val id: UUID,
    val name: String,
    val address: String,
    val metro: String,
    val time: String,
    val phone: String,
    val pickup: String,
    val isFavorite: Boolean,
    val coordinates: Pair<Double, Double>
)