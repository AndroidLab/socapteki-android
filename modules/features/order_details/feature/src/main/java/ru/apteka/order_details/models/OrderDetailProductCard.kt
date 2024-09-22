package ru.apteka.order_details.models

import ru.apteka.components.data.models.ProductModel

/**
 * Представляет карточку продукта в заказе.
 */
data class OrderDetailProductCard(
    val product: ProductModel,
    val onCardClick: (ProductModel) -> Unit
)
