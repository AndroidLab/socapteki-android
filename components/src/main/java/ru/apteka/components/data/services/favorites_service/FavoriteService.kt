package ru.apteka.components.data.services.favorites_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.utils.contains
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис избранного.
 */
@Singleton
class FavoriteService @Inject constructor(

) {
    private val _products = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     * Возвращает список продукции в избранном.
     */
    val products: LiveData<List<ProductCardModel>> = _products

    /**
     * Возвращает кол-во продукции в избранном.
     */
    val totalCount: LiveData<Int> = products.map {
        it.size
    }

    /**
     * Добавляет товар в избранное.
     */
    fun addProduct(productCard: ProductCardModel) {
        _products.value = _products.value!!.plus(productCard)
    }

    /**
     * Удаляет товар из избранного.
     */
    fun removeProduct(productCard: ProductCardModel) {
        _products.value = _products.value!!.minus(productCard)
    }

    /**
     * Возвращет флаг содержания продукции в избранном.
     */
    fun isContainsInFavorite(uuid: UUID) = _products.value!!.contains { it.product.id == uuid }
}