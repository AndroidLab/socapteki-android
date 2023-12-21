package ru.apteka.profile.presentation.comments_reviews

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Комментариии и подписки".
 */
@HiltViewModel
class CommentsReviewsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
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