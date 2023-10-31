package ru.apteka.components.data.models


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
    lateinit var favorite: FavoriteModel

    /**
     * Возвращает или устанавливает счетчик товара в корзине.
     */
    lateinit var itemCounter: ProductCounterModel
}
