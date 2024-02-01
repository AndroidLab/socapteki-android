package ru.apteka.components.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.favorites_service.FavoriteService

/**
 * Представляет модель избранного для карточки продукции.
 */
data class ProductFavoriteModel(
    private val favoriteService: FavoriteService,
    private val isFavorite: Boolean,
) {
    private val _isFavoriteLiveData = MutableLiveData(isFavorite)

    /**
     * Возвращает флаг наличия продукта в избранном.
     */
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    /**
     * Возвращает обработчик клина на иконке избранного.
     */
    fun clickFavorite(product: ProductModel) {
        if (favoriteService.isContainsInFavorite(product.id)) {
            favoriteService.removeProduct(product)
            _isFavoriteLiveData.value = false
        } else {
            favoriteService.addProduct(product)
            _isFavoriteLiveData.value = true
        }
    }
}
