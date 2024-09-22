package ru.apteka.charity.data.repository.charity

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param newModuleApi  api.
 */
class CharityRepository @Inject constructor(
    private val newModuleApi: ICharityApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}