package ru.apteka.components.data.services.favorites_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductModel
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
    private val _products = MutableLiveData<List<ProductModel>>(emptyList())

    /**
     * Возвращает список продукции в избранном.
     */
    val products: LiveData<List<ProductModel>> = _products

    /**
     * Возвращает кол-во продукции в избранном.
     */
    val totalCount: LiveData<Int> = products.map {
        it.size
    }

    /**
     * Добавляет товар в избранное.
     */
    fun addProduct(productCard: ProductModel) {
        _products.value = _products.value!!.plus(productCard)
    }

    /**
     * Удаляет товар из избранного.
     */
    fun removeProduct(product: ProductModel) {
        _products.value = _products.value!!.minus(product)
    }

    /**
     * Возвращет флаг содержания продукции в избранном.
     */
    fun isContainsInFavorite(uuid: UUID) = _products.value!!.contains { it.id == uuid }
}