package ru.apteka.barcode_scanner.data.repository.barcode_scanner

import kotlinx.coroutines.delay
import ru.apteka.new_module.data.repository.new_repository.IApi
import javax.inject.Inject

/**
 * Представляет репозиторий .
 * @param newModuleApi api.
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