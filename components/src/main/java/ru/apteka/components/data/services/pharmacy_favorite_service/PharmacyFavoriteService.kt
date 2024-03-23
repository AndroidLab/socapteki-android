package ru.apteka.components.data.services.pharmacy_favorite_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.apteka.components.data.models.PharmacyModel
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.contains
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис избранных аптек.
 */
@Singleton
class PharmacyFavoriteService @Inject constructor(

) {

    /**
     * Возвращает список продукции в избранном.
     */
    val pharmacies = ScopedLiveData(emptyList<PharmacyModel>())

    /**
     * Возвращает кол-во продукции в избранном.
     */
    val totalCount: LiveData<Int> = pharmacies.map {
        it.size
    }

    /**
     * Добавляет товар в избранное.
     */
    fun addPharmacy(pharmacy: PharmacyModel) {
        pharmacies.setValue(pharmacies.value!!.plus(pharmacy))
    }

    /**
     * Удаляет товар из избранного.
     */
    fun removePharmacy(pharmacy: PharmacyModel) {
        pharmacies.setValue(pharmacies.value!!.minus(pharmacy))
    }

    /**
     * Возвращет флаг содержания продукции в избранном.
     */
    fun isContainsInFavorite(uuid: UUID) = pharmacies.value!!.contains { it.id == uuid }
}