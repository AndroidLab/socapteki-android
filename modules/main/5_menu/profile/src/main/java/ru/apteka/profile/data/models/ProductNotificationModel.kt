package ru.apteka.profile.data.models

import androidx.lifecycle.LiveData
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductModel


/**
 * Представляет модель для карточки продукта.
 * @param product Продукт.
 */
data class ProductNotificationModel(
    val product: ProductModel
) {
    /**
     * Возвращает или устанавливает модель избранного.
     */
    lateinit var favoriteModel: FavoriteModel

    /**
     * Возвращает или устанавливает модель обработки избранного.
     */
    lateinit var onNotificationRemove: (ProductModel) -> Unit
}