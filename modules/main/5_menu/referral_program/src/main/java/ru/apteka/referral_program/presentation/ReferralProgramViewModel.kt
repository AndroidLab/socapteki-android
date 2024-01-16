package ru.apteka.referral_program.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
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
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val fakeHistoryRecommendation = listOf(
        HistoryRecommendationModel("12.01.23", "+150"),
        HistoryRecommendationModel("11.01.23", "+150"),
        HistoryRecommendationModel("08.01.23", "+150"),
        HistoryRecommendationModel("04.01.23", "+150"),
        HistoryRecommendationModel("01.01.23", "+150"),
    )

    private val _historyRecommendation = MutableLiveData<List<HistoryRecommendationModel>>(emptyList())

    /**
     *
     */
    val historyRecommendation: LiveData<List<HistoryRecommendationModel>> = _historyRecommendation

    private val _historyRecommendationIsLoading = MutableLiveData(false)

    /**
     *
     */
    val historyRecommendationIsLoading: LiveData<Boolean> = _historyRecommendationIsLoading

    init {
        viewModelScope.launchIO {
            _historyRecommendationIsLoading.postValue(true)
            delay(1500)
            _historyRecommendation.postValue(fakeHistoryRecommendation)
            _historyRecommendationIsLoading.postValue(false)
        }
    }

}