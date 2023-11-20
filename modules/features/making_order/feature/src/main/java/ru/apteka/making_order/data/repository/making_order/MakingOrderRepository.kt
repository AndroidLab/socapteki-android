package ru.apteka.making_order.data.repository.making_order

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class MakingOrderRepository @Inject constructor(
    private val newModuleApi: IMakingOrderApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}