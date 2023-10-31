package ru.apteka.components.data.models

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.favorites_service.FavoriteService

/**
 * Представляет модель избранного для карточки продукции.
 */
data class FavoriteModel(
    val favoriteService: FavoriteService,
    val isFavorite: Boolean,
) {
    /**
     * Возвращает или устанавливает значения кол-ва товара в корзине.
     */
    val isFavoriteLiveData = MutableLiveData(isFavorite)

    /**
     * Возвращает обработчик клина на иконке избранного.
     */
    val onFavoriteClick: (ProductCardModel) -> Unit = { productCard ->
        if (favoriteService.isContainsInFavorite(productCard.product.id)) {
            favoriteService.removeProduct(productCard)
            isFavoriteLiveData.postValue(false)
        } else {
            favoriteService.addProduct(productCard)
            isFavoriteLiveData.postValue(true)
        }
    }
}
