package ru.apteka.our_partners.data.repository.our_partners_repository

import kotlinx.coroutines.delay
import ru.apteka.our_partners.data.repository.our_partners_repository.IOurPartnersApi
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class OurPartnersRepository @Inject constructor(
    private val favoritesApi: IOurPartnersApi
) {

    /**
     * Получает избранные.
     */
    suspend fun getFavorites(): List<Unit> {
        delay(1500)
        return listOf()
    }


}