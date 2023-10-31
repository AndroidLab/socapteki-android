package ru.apteka.pharmacies_map.data.model

import java.util.UUID

/**
 * model
 */
data class PharmacyModel(
    val id: UUID,
    val title: String,
    val desc: String,
    val pickup: String,
    val isFavorite: Boolean,
    val coordinates: Pair<Double, Double>
)