package ru.apteka.home.data.models

import androidx.annotation.Keep
import ru.apteka.components.data.models.Label
import java.util.UUID


/**
 * Представляет модель для карточки рекламы.
 */
data class AdvertModel(
    val id: UUID,
    val title: String,
    val description: String,
    val labels: List<Label> = emptyList()
)