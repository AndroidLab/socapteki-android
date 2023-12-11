package ru.apteka.licenses.data.repository.licenses_repository

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class Repository @Inject constructor(
    private val licensesApi: ILicensesApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}