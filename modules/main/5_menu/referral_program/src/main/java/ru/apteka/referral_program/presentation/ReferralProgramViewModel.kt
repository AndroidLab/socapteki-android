package ru.apteka.referral_program.presentation

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.referral_program.data.models.HistoryRecommendationModel
import javax.inject.Inject


/**
 * Представляет модель представления "Реферальная программа".
 */
@HiltViewModel
class ReferralProgramViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val accountsPreferences: AccountsPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает аккаунт.
     */
    val account = accountsPreferences.accountFlow.asLiveData()

    private val fakeHistoryRecommendation = listOf(
        HistoryRecommendationModel("12.01.23", "+150"),
        HistoryRecommendationModel("11.01.23", "+150"),
        HistoryRecommendationModel("08.01.23", "+150"),
        HistoryRecommendationModel("04.01.23", "+150"),
        HistoryRecommendationModel("01.01.23", "+150"),
    )

    /**
     *
     */
    val historyRecommendation = ScopedLiveData(emptyList<HistoryRecommendationModel>())

    /**
     *
     */
    val historyRecommendationIsLoading = ScopedLiveData(false)

    init {
        viewModelScope.launchIO {
            historyRecommendationIsLoading.postValue(true)
            delay(1500)
            historyRecommendation.postValue(fakeHistoryRecommendation)
            historyRecommendationIsLoading.postValue(false)
        }
    }

}