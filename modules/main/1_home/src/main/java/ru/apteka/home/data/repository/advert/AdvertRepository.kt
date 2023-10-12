package ru.apteka.home.data.repository.advert

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.LabelModel
import ru.apteka.home.data.models.AdvertModel
import ru.apteka.resources.R
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий рекламы.
 * @param advertApi Advert api.
 */
@ViewModelScoped
class AdvertRepository @Inject constructor(
    private val advertApi: IAdvertApi
) {

    /**
     * Получает рекламу.
     */
    suspend fun getAdvert(): List<AdvertModel> {
        delay(2000)
        return listOf(
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 1",
                description = "Скидки до 35% с бонусной картой",
                labels = listOf(
                    LabelModel(
                        text = "Реклама",
                        color = R.color.gold
                    ),
                    LabelModel(
                        text = "Реклама",
                        color = R.color.grey
                    ),
                    LabelModel(
                        text = "Реклама",
                        color = R.color.red
                    ),
                ),

            ),
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 2",
                description = "Скидки до 15% с бонусной картой",
                labels = emptyList()
            ),
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 3",
                description = "Скидки до 35% с бонусной картой",
                labels = listOf(
                    LabelModel(
                        text = "Товар дня",
                        color = R.color.color_primary
                    ),
                    LabelModel(
                        text = "Хит продаж",
                        color = R.color.color_primary_variant
                    )
                )
            ),
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 4",
                description = "Скидки до 99% с бонусной картой",
            ),
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 5",
                description = "Скидки до 10% с бонусной картой",
            )
        )
    }


}