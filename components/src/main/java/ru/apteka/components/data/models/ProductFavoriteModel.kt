package ru.apteka.components.data.models

import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.utils.ScopedLiveData

/**
 * Представляет модель избранного для карточки продукции.
 */
data class ProductFavoriteModel(
    private val favoriteService: FavoriteService,
    private val isFavorite: Boolean,
) {
    /**
     * Возвращает флаг наличия продукта в избранном.
     */
    val isFavoriteLiveData = ScopedLiveData(isFavorite)

    /**
     * Возвращает обработчик клина на иконке избранного.
     */
    fun clickFavorite(product: ProductModel) {
        if (favoriteService.isContainsInFavorite(product.id)) {
            favoriteService.removeProduct(product)
            isFavoriteLiveData.setValue(false)
        } else {
            favoriteService.addProduct(product)
            isFavoriteLiveData.setValue(true)
        }
    }
}
