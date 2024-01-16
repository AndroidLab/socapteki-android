package ru.apteka.listing.data.repository.listing

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class Repository @Inject constructor(
    private val newModuleApi: IListingApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}