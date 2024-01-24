package ru.apteka.feedback.data

import android.net.Uri


/**
 * Представляет файл.
 */
data class FileModel(
    val name: String,
    val uri: Uri,
    val onRemove: (FileModel) -> Unit
)
