package ru.apteka.licenses.data.model


/**
 * Представляет модель лицензии.
 */
data class LicenseModel(
    val title: String,
    val onItemClick: (LicenseModel) -> Unit
)
