package ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class PharmaciesMapRepository @Inject constructor(
    private val pharmaciesMapApi: IPharmaciesMapApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}