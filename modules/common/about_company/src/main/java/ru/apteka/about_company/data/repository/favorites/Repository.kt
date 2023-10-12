package ru.apteka.about_company.data.repository.favorites

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class Repository @Inject constructor(
    private val favoritesApi: IApi
) {

    /**
     * Получает избранные.
     */
    suspend fun getFavorites(): List<Unit> {
        delay(1500)
        return listOf()
    }


}