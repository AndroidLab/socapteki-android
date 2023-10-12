package ru.apteka.advantages.data.repository.advantages_repository

import IAdvantageApi
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class AdvantagesRepository @Inject constructor(
    //private val advantageApi: IAdvantageApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}