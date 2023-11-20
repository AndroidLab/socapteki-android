package ru.apteka.contacts.data.repository.contacts

import kotlinx.coroutines.delay
import ru.apteka.contacts.data.repository.contacts.IContactsApi
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param Api  api.
 */
class Repository @Inject constructor(
    private val newModuleApi: IContactsApi
) {

    /**
     * Получает .
     */
    suspend fun get(): List<Unit> {
        delay(1500)
        return listOf()
    }


}