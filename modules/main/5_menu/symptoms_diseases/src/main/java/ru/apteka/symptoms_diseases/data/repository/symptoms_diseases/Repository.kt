package ru.apteka.symptoms_diseases.data.repository.symptoms_diseases

import kotlinx.coroutines.delay
import ru.apteka.symptoms_diseases.data.repository.symptoms_diseases.IApi
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