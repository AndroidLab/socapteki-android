package ru.apteka.home.data.models

import ru.apteka.components.data.models.LabelModel
import ru.apteka.resources.R
import java.util.UUID
import kotlin.random.Random


/**
 * Представляет модель для карточки рекламы.
 */
data class AdvertModel(
    val id: UUID,
    val title: String,
    val description: String,
    val color: Int = Random.let { rnd -> listOf(R.color.color_primary, R.color.orange, R.color.dark_black)[rnd.nextInt(3)] },
    val labels: List<LabelModel> = emptyList()
)