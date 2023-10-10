package ru.apteka.favorites.presentation.favorites.data.repository.favorites

import kotlinx.coroutines.delay
import ru.apteka.favorites.presentation.favorites.data.models.FavoriteModel
import ru.apteka.resources.R
import java.util.UUID
import javax.inject.Inject

/**
 * Представляет репозиторий избранных.
 * @param favoritesApi Favorites api.
 */
class FavoritesRepository @Inject constructor(
    private val favoritesApi: IFavoritesApi
) {

    /**
     * Получает избранные.
     */
    suspend fun getFavorites(): List<FavoriteModel> {
        delay(1500)
        return listOf(
            FavoriteModel(
                id = UUID.randomUUID(),
                imageSrc = "https://social-apteka.ru/upload/ammina.optimizer/png-webp/q80/upload/resize_cache/iblock/88a/160_160_0/88a5ad56c9e83bbcc3000ad460521b.webp",
                title = "Низорал шампунь 2% 60мл (Нижфарм)",
                description = "Активный компонент диклофенак – нестероидный противовоспалительный препарат",
                labels = listOf(
                    FavoriteModel.FavoriteLabel(
                        text = "Товар дня",
                        color = R.color.color_primary
                    ),
                    FavoriteModel.FavoriteLabel(
                        text = "Хит продаж",
                        color = R.color.color_primary_variant
                    ),
                    FavoriteModel.FavoriteLabel(
                        text = "Марка рекламы",
                        color = R.color.dark_grey
                    ),
                    FavoriteModel.FavoriteLabel(
                        text = "Марка рекламы",
                        color = R.color.gold
                    ),
                    FavoriteModel.FavoriteLabel(
                        text = "Марка рекламы",
                        color = R.color.light_green
                    ),
                ),
                price = "2 200 ₽",
                oldPrice = "от 5 200 ₽",
                discount = "30%"
            ),
            FavoriteModel(
                id = UUID.randomUUID(),
                imageSrc = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/7d9/160_160_0/7d9f03b772dfb242d60b26498406bafd.webp",
                title = "Низорал шампунь 2% 60мл (Нижфарм)",
                description = "Активный компонент диклофенак – нестероидный противовоспалительный препарат",
                labels = emptyList(),
                price = "2 200 ₽",
                oldPrice = "от 5 200 ₽",
                discount = "30 %"
            ),
            FavoriteModel(
                id = UUID.randomUUID(),
                imageSrc = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/150/160_160_0/1502eaf7297014c4e8614da9c1a63cef.webp",
                title = "Низорал шампунь 2% 60мл (Нижфарм)",
                description = "Активный компонент диклофенак – нестероидный противовоспалительный препарат",
                labels = listOf(
                    FavoriteModel.FavoriteLabel(
                        text = "Товар дня",
                        color = R.color.color_primary
                    ),
                    FavoriteModel.FavoriteLabel(
                        text = "Хит продаж",
                        color = R.color.color_primary_variant
                    )
                ),
                price = "2 200 ₽",
                oldPrice = "от 5 200 ₽",
                discount = "30 %"
            ),
            FavoriteModel(
                id = UUID.randomUUID(),
                imageSrc = "https://social-apteka.ru/upload/ammina.optimizer/png-webp/q80/upload/resize_cache/iblock/816/160_160_0/816ccd79cbe50269852e94619e73f9f8.webp",
                title = "Низорал шампунь 2% 60мл (Нижфарм)",
                description = "Активный компонент диклофенак – нестероидный противовоспалительный препарат",
                price = "2 200 ₽",
            ),
            FavoriteModel(
                id = UUID.randomUUID(),
                imageSrc = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/eb6/160_160_0/eb6fd745730f175591a7a411bb127cb3.webp",
                title = "Низорал шампунь 2% 60мл (Нижфарм)",
                description = "Активный компонент диклофенак – нестероидный противовоспалительный препарат",
                price = "2 200 ₽",
                oldPrice = "от 5 200 ₽",
                discount = "30 %"
            ),
            FavoriteModel(
                id = UUID.randomUUID(),
                imageSrc = "https://social-apteka.ru/upload/ammina.optimizer/png-webp/q80/upload/resize_cache/iblock/own/160_160_0/PREV_11682-1.webp",
                title = "Низорал шампунь 2% 60мл (Нижфарм)",
                description = "Активный компонент диклофенак – нестероидный противовоспалительный препарат",
                labels = listOf(
                    FavoriteModel.FavoriteLabel(
                        text = "Товар дня",
                        color = R.color.color_primary
                    ),
                    FavoriteModel.FavoriteLabel(
                        text = "Хит продаж",
                        color = R.color.color_primary_variant
                    )
                ),
                price = "2 200 ₽",
                oldPrice = null,
                discount = null
            )
        )
    }


}