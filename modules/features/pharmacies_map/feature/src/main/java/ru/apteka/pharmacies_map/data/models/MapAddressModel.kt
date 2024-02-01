package ru.apteka.pharmacies_map.data.models

import java.util.UUID


/**
 * Представляет адрес на карте.
 */
data class MapAddressModel(
    val title: String,
    val onItemClick: () -> Unit,
    val id: UUID = UUID.randomUUID()
)
