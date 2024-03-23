package ru.apteka.components.data.models

import ru.apteka.components.data.services.pharmacy_favorite_service.PharmacyFavoriteService
import ru.apteka.components.data.utils.ScopedLiveData

/**
 * Представляет модель избранного для карточки аптеки.
 */
data class PharmacyFavoriteModel(
    private val favoriteService: PharmacyFavoriteService,
    private val isFavorite: Boolean,
) {
    /**
     * Возвращает флаг наличия аптеки в избранном.
     */
    val isFavoriteLiveData = ScopedLiveData(isFavorite)

    /**
     * Возвращает обработчик клина на иконке избранного.
     */
    fun clickFavorite(pharmacy: PharmacyModel) {
        if (favoriteService.isContainsInFavorite(pharmacy.id)) {
            favoriteService.removePharmacy(pharmacy)
            isFavoriteLiveData.setValue(false)
        } else {
            favoriteService.addPharmacy(pharmacy)
            isFavoriteLiveData.setValue(true)
        }
    }
}
