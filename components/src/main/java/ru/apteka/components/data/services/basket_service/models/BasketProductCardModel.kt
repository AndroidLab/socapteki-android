package ru.apteka.components.data.services.basket_service.models

import ru.apteka.components.data.models.ProductCardModel


/**
 * Представляет модель продукта в корзине.
 */
data class BasketProductCardModel(
    val productCard: ProductCardModel,
    val onRemoveProducts: (ProductCardModel) -> Unit
)
