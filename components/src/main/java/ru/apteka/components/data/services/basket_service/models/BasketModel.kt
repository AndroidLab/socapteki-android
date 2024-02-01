package ru.apteka.components.data.services.basket_service.models

import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.basket_service.BasketService

/**
 * Представляет модель продукта в корзине.
 */
data class BasketModel(
    private val basketService: BasketService,
    private val countInBasket: Int
) {

    /**
     * Возвращает обработчик уменьшения товара в корзине.
     */
    val onMinus: (ProductModel) -> Unit = { product ->
        product.setCount(product.countInBasketLiveData.value!! - 1)
        if (product.countInBasketLiveData.value == 0) {
            basketService.removeProduct(product)
        } else {
            basketService.updateTotalCount()
        }
    }

    /**
     * Возвращает обработчик увеличения товара в корзине.
     */
    val onPlus: (ProductModel) -> Unit = { product ->
        product.setCount(product.countInBasketLiveData.value!! + 1)
        if (basketService.isContainsInBasket(product.id)) {
            basketService.updateTotalCount()
        } else {
            basketService.addProduct(product)
        }
    }

    /**
     * Очистить значение.
     */
    fun clear(product: ProductModel) {
        basketService.removeProduct(product)
    }
}
