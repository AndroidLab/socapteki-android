package ru.apteka.profile.data.models

import ru.apteka.components.data.models.ProductFavoriteModel
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
    lateinit var favoriteModel: ProductFavoriteModel

    /**
     * Возвращает или устанавливает модель обработки избранного.
     */
    lateinit var onNotificationRemove: (ProductModel) -> Unit
}