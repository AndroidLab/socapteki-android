package ru.apteka.loyalty_program.data.repository.loyalty_program

import kotlinx.coroutines.delay
import ru.apteka.loyalty_program.data.repository.loyalty_program.IApi
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class Repository @Inject constructor(
    private val newModuleApi: IApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}