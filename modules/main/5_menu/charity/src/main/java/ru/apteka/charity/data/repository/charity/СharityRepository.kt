package ru.apteka.feedback.data.repository.feedback

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class СharityRepository @Inject constructor(
    private val newModuleApi: IСharityApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}