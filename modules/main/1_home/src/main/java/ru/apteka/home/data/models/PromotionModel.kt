package ru.apteka.home.data.models

import ru.apteka.components.data.models.LabelModel
import ru.apteka.resources.R
import java.util.UUID
import kotlin.random.Random


/**
 * Представляет модель для карточки акции.
 */
data class PromotionModel(
    val id: UUID,
    val title: String,
    val description: String,
    val labels: List<LabelModel> = emptyList()
)