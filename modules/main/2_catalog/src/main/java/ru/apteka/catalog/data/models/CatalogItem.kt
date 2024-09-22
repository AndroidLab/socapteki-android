package ru.apteka.catalog.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID


/**
 * Представляет пункт каталога.
 */
@Parcelize
data class CatalogItem(
    val title: String,
    val image: String? = null,
    val subItems: List<String> = emptyList(),
    val id: UUID = UUID.randomUUID()
): Parcelable
