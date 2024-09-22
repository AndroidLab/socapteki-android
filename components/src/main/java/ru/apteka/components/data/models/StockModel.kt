package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID


/**
 * Представляет модель для карточки избранного.
 */
@Parcelize
data class StockModel(
    val imageSrc: String,
    val title: String,
    val date: String,
    val description: String,
    val labels: List<Label> = emptyList(),
    val id: UUID = UUID.randomUUID()
): Parcelable