package ru.apteka.profile.presentation.comments_reviews.pages.feedback

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Обратная связь".
 */
@HiltViewModel
class FeedbackPageViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : MainScreenBaseViewModel(
    navigationManager,
    messageNoticeService
) {



    init {
        /*viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { loginRepository.getSubscriptions() },
                onSuccess = { subscriptions ->
                    mainThread {

                    }
                },
                isLoading = _isLoading
            )
        }*/
    }




}