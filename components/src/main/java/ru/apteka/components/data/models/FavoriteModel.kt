package ru.apteka.components.data.models

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.favorites_service.FavoriteService

/**
 * Представляет модель избранного для карточки продукции.
 */
data class FavoriteModel(
    private val favoriteService: FavoriteService,
    private val isFavorite: Boolean,
) {
    /**
     * Возвращает или устанавливает значения кол-ва товара в корзине.
     */
    val isFavoriteLiveData = MutableLiveData(isFavorite)

    /**
     * Возвращает обработчик клина на иконке избранного.
     */
    fun clickFavorite(product: ProductModel) {
        if (favoriteService.isContainsInFavorite(product.id)) {
            favoriteService.removeProduct(product)
            isFavoriteLiveData.value = false
        } else {
            favoriteService.addProduct(product)
            isFavoriteLiveData.value = true
        }
    }
}
