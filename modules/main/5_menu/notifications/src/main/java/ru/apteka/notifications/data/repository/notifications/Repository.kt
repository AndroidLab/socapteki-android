package ru.apteka.notifications.data.repository.notifications

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param newModuleApi  api.
 */
class Repository @Inject constructor(
    private val newModuleApi: INotificationsApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}