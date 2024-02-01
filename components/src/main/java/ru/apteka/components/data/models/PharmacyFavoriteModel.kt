package ru.apteka.components.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.pharmacy_favorite_service.PharmacyFavoriteService

/**
 * Представляет модель избранного для карточки аптеки.
 */
data class PharmacyFavoriteModel(
    private val favoriteService: PharmacyFavoriteService,
    private val isFavorite: Boolean,
) {
    private val _isFavoriteLiveData = MutableLiveData(isFavorite)

    /**
     * Возвращает флаг наличия аптеки в избранном.
     */
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    /**
     * Возвращает обработчик клина на иконке избранного.
     */
    fun clickFavorite(pharmacy: PharmacyModel) {
        if (favoriteService.isContainsInFavorite(pharmacy.id)) {
            favoriteService.removePharmacy(pharmacy)
            _isFavoriteLiveData.value = false
        } else {
            favoriteService.addPharmacy(pharmacy)
            _isFavoriteLiveData.value = true
        }
    }
}
