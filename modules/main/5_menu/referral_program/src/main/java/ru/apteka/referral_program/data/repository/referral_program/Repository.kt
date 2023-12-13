package ru.apteka.referral_program.data.repository.referral_program

import kotlinx.coroutines.delay
import ru.apteka.referral_program.data.repository.referral_program.IApi
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