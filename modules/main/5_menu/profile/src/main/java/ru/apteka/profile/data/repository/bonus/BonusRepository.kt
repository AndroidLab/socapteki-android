package ru.apteka.profile.data.repository.bonus

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.profile.data.models.BonusModel
import javax.inject.Inject

/**
 * Представляет репозиторий бонусов.
 * @param bonusApi Bonus api.
 */
@ViewModelScoped
class BonusRepository @Inject constructor(
    private val bonusApi: IBonusApi
) {

    /**
     * Получает историю бонусов.
     */
    suspend fun getBonusHistory(): List<BonusModel> {
        delay(1500)
        return listOf(
            BonusModel(
                title = "Начисление",
                date = 1697711146,
                value = 320,
                desc = "Интернет заказ"
            ),
            BonusModel(
                title = "Начисление",
                date = 1697711146,
                value = 320,
                desc = "Интернет заказ"
            ),
            BonusModel(
                title = "Начисление",
                date = 1697711146,
                value = -320,
                desc = "Покупка в розницу"
            ),
            BonusModel(
                title = "Начисление",
                date = 1697711146,
                value = 320,
                desc = "Интернет заказ"
            ),
            BonusModel(
                title = "Списание",
                date = 1697711146,
                value = -320,
                desc = "Покупка в розницу"
            ),
        )
    }


}