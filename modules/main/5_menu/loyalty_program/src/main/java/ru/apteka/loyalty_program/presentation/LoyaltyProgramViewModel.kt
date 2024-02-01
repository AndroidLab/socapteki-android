package ru.apteka.loyalty_program.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject

/**
 * Представляет модель представления "Программа лояльности".
 */
@HiltViewModel
class LoyaltyProgramViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val fakeBanners = MutableLiveData(
        listOf(
            Unit,
            Unit,
            Unit,
        )
    )

    /**
     *
     */
    val banners: LiveData<List<Unit>> = fakeBanners

    init {
        /*viewModelScope.launchIO {
            _historyRecommendationIsLoading.postValue(true)
            delay(1500)
            _historyRecommendation.postValue(fakeHistoryRecommendation)
            _historyRecommendationIsLoading.postValue(false)
        }*/
    }
}
