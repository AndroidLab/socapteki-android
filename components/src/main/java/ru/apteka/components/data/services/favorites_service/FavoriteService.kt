package ru.apteka.components.data.services.favorites_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.ScopedLiveData
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

    /**
     * Возвращает список продукции в избранном.
     */
    val products = ScopedLiveData(emptyList<ProductModel>())

    /**
     * Возвращает кол-во продукции в избранном.
     */
    val totalCount: LiveData<Int> = products.map {
        it.size
    }

    /**
     * Добавляет товар в избранное.
     */
    fun addProduct(product: ProductModel) {
        products.setValue(products.value!!.plus(product))
    }

    /**
     * Удаляет товар из избранного.
     */
    fun removeProduct(product: ProductModel) {
        products.setValue(products.value!!.minus(product))
    }

    /**
     * Возвращет флаг содержания продукции в избранном.
     */
    fun isContainsInFavorite(uuid: UUID) = products.value!!.contains { it.id == uuid }
}