package ru.apteka.brands.data.repository.brands

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param newModuleApi  api.
 */
class BrandsRepository @Inject constructor(
    private val newModuleApi: IBrandsApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}