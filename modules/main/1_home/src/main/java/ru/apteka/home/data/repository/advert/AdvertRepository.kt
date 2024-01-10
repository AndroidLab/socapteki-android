package ru.apteka.home.data.repository.advert

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.models.Label
import ru.apteka.home.data.models.AdvertModel
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

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
        delay(500)
        return listOf(
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 1",
                description = "Скидки до 35% с бонусной картой",
                labels = listOf(
                    Label.ADVERT,
                    Label.CHECKED_SPECIALIST,
                    Label.PRODUCT_DAY
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
                    Label.ADVERT,
                    Label.CHECKED_SPECIALIST,
                    Label.PRODUCT_DAY

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
            ),
            AdvertModel(
                id = UUID.randomUUID(),
                title = "Осейняя забота 6",
                description = "Скидки до 10% с бонусной картой",
            ),
        )
    }


}