package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID


/**
 * Представляет модель для карточки продукта.
 */
@Parcelize
data class ProductModel(
    val id: UUID,
    val image: String,
    val isFavorite: Boolean,
    val price: String,
    val desc: String,
    val rating: String,
    val comments: Int,
    val images: List<String> = listOf(),
    val discount: DiscountModel? = null,
    val additionalDesc: String? = null,
    val releaseForm: String? = null,
    val manufacturer: String? = null,
    val activeSubstance: String? = null,
    val dosage: String? = null,
    val expirationDate: String? = null,
    val pickupDate: String? = null,
    val homeDeliveryDate: String? = null,
    val variants: @RawValue List<ProductVariantModel> = emptyList(),
    val labels: List<Label> = emptyList(),
    val countInBasket: Int = 0,
) : Parcelable