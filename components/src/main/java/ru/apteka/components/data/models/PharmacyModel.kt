package ru.apteka.components.data.models

import java.util.UUID

/**
 * model
 */
data class PharmacyModel(
    val id: UUID,
    val name: String,
    val address: String,
    val pickup: String,
    val isFavorite: Boolean,
    val coordinates: Pair<Double, Double>
)