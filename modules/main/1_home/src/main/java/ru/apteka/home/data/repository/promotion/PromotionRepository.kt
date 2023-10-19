package ru.apteka.home.data.repository.promotion

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.models.LabelModel
import ru.apteka.home.data.models.PromotionModel
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий акций.
 * @param promotionApi Promotion api.
 */
@ViewModelScoped
class PromotionRepository @Inject constructor(
    private val promotionApi: IPromotionApi
) {

    /**
     * Получает акции.
     */
    suspend fun getPromotions(): List<PromotionModel> {
        delay(1500)
        return listOf(
            PromotionModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота",
                description = "Скидки до 35% с бонусной картой",
                labels = listOf(
                    LabelModel(
                        text = "Реклама",
                        color = R.color.gold
                    )
                ),

            ),
            PromotionModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота",
                description = "Скидки до 15% с бонусной картой",
                labels = emptyList()
            ),
            PromotionModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота",
                description = "Скидки до 35% с бонусной картой",
                labels = listOf(
                    LabelModel(
                        text = "Товар дня",
                        color = R.color.color_primary
                    )
                )
            )
        )
    }


}