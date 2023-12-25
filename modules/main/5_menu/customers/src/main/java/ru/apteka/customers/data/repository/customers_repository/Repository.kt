package ru.apteka.customers.data.repository.customers_repository

import kotlinx.coroutines.delay
import ru.apteka.customers.data.repository.customers_repository.ICustomersApi
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class Repository @Inject constructor(
    private val licensesApi: ICustomersApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}