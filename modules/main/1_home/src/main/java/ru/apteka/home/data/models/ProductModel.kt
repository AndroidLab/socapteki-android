package ru.apteka.home.data.models

import ru.apteka.components.data.models.LabelModel
import java.util.UUID


/**
 * Представляет модель для карточки продукта.
 */
data class ProductModel(
    val id: UUID,
    val image: String,
    val isFavorite: Boolean,
    val price: String,
    val desc: String,
    val rating: String,
    val comments: Int,
    val discount: DiscountModel? = null,
    val additionalDesc: String? = null,
    val labels: List<LabelModel> = emptyList()
)