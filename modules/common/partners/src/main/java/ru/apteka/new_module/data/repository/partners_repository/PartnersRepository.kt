package ru.apteka.new_module.data.repository.partners_repository

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class PartnersRepository @Inject constructor(
    private val newModuleApi: IPartnersApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}