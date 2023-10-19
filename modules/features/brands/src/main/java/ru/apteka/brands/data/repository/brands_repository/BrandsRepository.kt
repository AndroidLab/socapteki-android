package ru.apteka.brands.data.repository.brands_repository

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class BrandsRepository @Inject constructor(
    private val favoritesApi: IBrandsApi
) {

    /**
     * Получает избранные.
     */
    suspend fun getFavorites(): List<Unit> {
        delay(1500)
        return listOf()
    }


}