package ru.apteka.favorites.data.model

import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.models.ProductModel


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
    lateinit var itemCounter: ProductCounterModel
}
