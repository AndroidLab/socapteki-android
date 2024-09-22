package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ru.apteka.components.data.utils.ScopedLiveData
import java.util.UUID

/**
 * Представляет модель для карточки продукта.
 */
@Parcelize
data class ProductModel(
    val image: String,
    val isFavorite: Boolean,
    val price: String,
    val title: String,
    val rating: String,
    val comments: Int,
    val needRecipe: Boolean = false,
    val images: List<String> = listOf(),
    val discount: DiscountModel? = null,
    val desc: String? = null,
    val additionalDesc: String? = null,
    val releaseForm: String? = null,
    val manufacturer: String? = null,
    val manufacturerCountry: String? = null,
    val activeSubstance: String? = null,
    val dosage: String? = null,
    val expirationDate: String? = null,
    val pickupDate: String? = null,
    val homeDeliveryDate: String? = null,
    val variants: @RawValue List<ProductVariantModel> = emptyList(),
    val labels: List<Label> = emptyList(),
    val disclaimer: String? = null,
    val countInBasket: Int = 0,
    val id: UUID = UUID.randomUUID(),
) : Parcelable {

    /**
     * Зеленый текст для некоторых карточек.
     */
    @IgnoredOnParcel
    var greenDesc: String? = null

    /**
     * Возвращает или устанавливает значения кол-ва товара в корзине.
     */
    @IgnoredOnParcel
    val countInBasketLiveData = ScopedLiveData(countInBasket)

    /**
     * Устанавливает кол-во товаров в корзине.
     */
    fun setCount(count: Int) {
        countInBasketLiveData.setValue(
            if (count < 0) 0 else count
        )
    }
}
