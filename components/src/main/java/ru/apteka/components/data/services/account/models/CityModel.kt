package ru.apteka.components.data.services.account.models

import java.util.UUID


/**
 * Представляет модель города.
 */
data class CityModel(
    val id: UUID,
    val name: String,
    val subtitle: String
)