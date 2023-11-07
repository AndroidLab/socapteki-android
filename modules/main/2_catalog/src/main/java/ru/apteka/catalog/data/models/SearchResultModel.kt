package ru.apteka.catalog.data.models


/**
 * Представляет результат поиска продукции.
 */
data class SearchResultModel(
    val type: SearchResultType,
    val text: String
) {
    enum class SearchResultType() {
        HISTORY,
        RESULT
    }
}
