package ru.apteka.legal_documents.data.repository.legal_documents_repository

import kotlinx.coroutines.delay
import ru.apteka.licenses.data.repository.licenses_repository.ILicensesApi
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