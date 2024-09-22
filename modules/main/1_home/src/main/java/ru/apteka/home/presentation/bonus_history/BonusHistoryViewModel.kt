package ru.apteka.home.presentation.bonus_history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.BonusModel
import ru.apteka.home.data.models.BonusStubModel
import ru.apteka.home.presentation.home.HomeViewModel
import javax.inject.Inject

/**
 * Представляет модель представления "История бонусов".
 */
@HiltViewModel
class BonusHistoryViewModel @Inject constructor(
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val fakeBonuses = listOf(
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


    /**
     * Возвращает историю бонусов.
     */
    val bonuses = ScopedLiveData(emptyList<BonusModel>())

    /**
     * Возвращает историю бонусов заглушек для не зарегистрированного пользователя.
     */
    val bonusesStub = ScopedLiveData(
        listOf(
            BonusStubModel(
                titleLength = 80,
                descLength = 80,
                isProfit = true
            ),
            BonusStubModel(
                titleLength = 80,
                descLength = 100,
                isProfit = true
            ),
            BonusStubModel(
                titleLength = 60,
                descLength = 80,
                isProfit = false
            ),
            BonusStubModel(
                titleLength = 60,
                descLength = 100,
                isProfit = false
            ),
            BonusStubModel(
                titleLength = 80,
                descLength = 80,
                isProfit = true
            )
        )
    )

    init {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(750)
            bonuses.postValue(fakeBonuses)
            isLoading.postValue(false)
        }
    }
}
