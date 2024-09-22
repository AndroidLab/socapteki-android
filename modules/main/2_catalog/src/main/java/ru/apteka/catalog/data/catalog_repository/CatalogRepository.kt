package ru.apteka.catalog.data.catalog_repository

import kotlinx.coroutines.delay
import ru.apteka.catalog.data.models.SearchResultModel
import javax.inject.Inject

/**
 * Представляет репозиторий каталога.
 */
class CatalogRepository @Inject constructor(

) {

    private val searchFake = listOf(
        SearchResultModel(
            type = SearchResultModel.SearchResultType.RESULT,
            text = "горло ангидак"
        ),
        SearchResultModel(
            type = SearchResultModel.SearchResultType.RESULT,
            text = "горло гелангин"
        ),
        SearchResultModel(
            type = SearchResultModel.SearchResultType.RESULT,
            text = "горло звездочка"
        ),
    )

    /**
     * Возвращает результаты поиска.
     */
    suspend fun searchResult(q: String): List<SearchResultModel> {
        delay(1500)
        return searchFake.filter { it.text.contains(q, true) }
    }

}