package ru.apteka.favorites.presentation.favorites.data.models

import androidx.annotation.DrawableRes
import java.util.UUID


/**
 * Представляет модель для карточки избранного.
 */
data class FavoriteModel(
    val id: UUID,
    val imageSrc: String,
    val title: String,
    val description: String,
    val labels: List<FavoriteLabel> = emptyList(),
    val price: String? = null,
    val oldPrice: String? = null,
    val discount: String? = null
) {
    data class FavoriteLabel(
        val text: String,
        @DrawableRes val color: Int
    )
}