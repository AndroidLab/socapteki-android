package ru.apteka.catalog.data.models


/**
 * Представляет модель для заголовка истории поиска продукции.
 */
data class SearchHistoryHeaderModel(
    val onClearHistory: () -> Unit
)