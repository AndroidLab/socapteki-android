package ru.apteka.components.data.models

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.basket_service.BasketService


/**
 * Представляет модель счетчика продукции.
 */
data class ProductCounterModel(
    val basketService: BasketService,
    val productCard: ProductCardModel,
    val countInBasket: Int,
) {
    /**
     * Возвращает или устанавливает значения кол-ва товара в корзине.
     */
    val counterLiveData = MutableLiveData(countInBasket)

    /**
     * Возвращает обработчик уменьшения товара в корзине.
     */
    val onMinus: () -> Unit = {
        val newVal = counterLiveData.value!! - 1
        counterLiveData.postValue(if (newVal < 0) 0 else newVal)
        if (newVal == 0) {
            basketService.removeProduct(productCard)
        }
    }

    /**
     * Возвращает обработчик увеличения товара в корзине.
     */
    val onPlus: () -> Unit = {
        val newVal = counterLiveData.value!! + 1
        counterLiveData.postValue(newVal)
        if (!basketService.isContainsInBasket(productCard.product.id)) {
            basketService.addProduct(productCard)
        }
    }

    /**
     * Очистить значение.
     */
    fun clear() {
        counterLiveData.postValue(0)
        basketService.removeProduct(productCard)
    }
}
