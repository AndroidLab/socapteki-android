package ru.apteka.components.data.services.pharmacy_favorite_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.apteka.components.data.models.PharmacyModel
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
    private val _pharmacies = MutableLiveData<List<PharmacyModel>>(emptyList())

    /**
     * Возвращает список продукции в избранном.
     */
    val pharmacies: LiveData<List<PharmacyModel>> = _pharmacies

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
        _pharmacies.value = _pharmacies.value!!.plus(pharmacy)
    }

    /**
     * Удаляет товар из избранного.
     */
    fun removePharmacy(pharmacy: PharmacyModel) {
        _pharmacies.value = _pharmacies.value!!.minus(pharmacy)
    }

    /**
     * Возвращет флаг содержания продукции в избранном.
     */
    fun isContainsInFavorite(uuid: UUID) = _pharmacies.value!!.contains { it.id == uuid }
}