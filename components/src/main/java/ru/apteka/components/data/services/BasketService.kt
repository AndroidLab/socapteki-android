package ru.apteka.components.data.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис корзины.
 */
@Singleton
class BasketService @Inject constructor(

) {

    private val _products = MutableLiveData<List<UUID>>(emptyList())

    /**
     * Возвращает список продукции.
     */
    val products: LiveData<List<UUID>> = _products

    /**
     *
     */
    fun addProduct(product: UUID) {
        _products.value = _products.value!!.plus(product)
    }

    /**
     *
     */
    fun removeProduct(product: UUID) {
        _products.value = _products.value!!.minus(product)
    }

    /**
     *
     */
    fun isContainsInBasket(uuid: UUID) = _products.value!!.contains(uuid)
}