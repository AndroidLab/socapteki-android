package ru.apteka.favorites.presentation.stocks.data.models

import ru.apteka.components.data.models.Label
import java.util.UUID


/**
 * Представляет модель для карточки избранного.
 */
data class FavoriteModel(
    val id: UUID,
    val imageSrc: String,
    val title: String,
    val description: String,
    val labels: List<Label> = emptyList(),
    val price: String? = null,
    val oldPrice: String? = null,
    val discount: String? = null
)