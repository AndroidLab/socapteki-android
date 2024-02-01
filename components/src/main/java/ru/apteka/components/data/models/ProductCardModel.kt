package ru.apteka.components.data.models

import ru.apteka.components.data.services.basket_service.models.BasketModel


/**
 * Представляет модель для карточки продукта.
 * @param product Продукт.
 */
data class ProductCardModel(
    val product: ProductModel
) {
    /**
     * Возвращает или устанавливает модель обработки избранного.
     */
    lateinit var favorite: ProductFavoriteModel

    /**
     * Возвращает или устанавливает модель обработки корзины.
     */
    lateinit var basket: BasketModel
}
