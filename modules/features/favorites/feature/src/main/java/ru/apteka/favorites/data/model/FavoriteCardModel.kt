package ru.apteka.favorites.data.model

import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.basket_service.models.BasketModel


/**
 * Представляет модель для карточки продукта.
 * @param product Продукт.
 */
data class FavoriteCardModel(
    val product: ProductModel
) {
    /**
     * Возвращает или устанавливает обработчик нажатия на иконке избранного.
     */
    lateinit var onFavoriteClick: (ProductModel) -> Unit

    /**
     * Возвращает или устанавливает счетчик товара в корзине.
     */
    lateinit var basket: BasketModel
}
