package ru.apteka.social.data.models


/**
 * Представляет данные новой версии файла.
 */
data class NewVersionFile(
    val version: Float,
    val doc: Docs.Response.Item
)
