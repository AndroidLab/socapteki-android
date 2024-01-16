package ru.apteka.profile.presentation.comments_reviews.pages.feedback

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
import ru.apteka.profile.data.models.FeedbackModel
import javax.inject.Inject


/**
 * Представляет модель представления "Обратная связь".
 */
@HiltViewModel
class FeedbackPageViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val feedbacksFake = listOf(
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
        FeedbackModel(
            title = "Осуществляется ли доставка по выходным?",
            text = "Да. Мы осуществляем доставку по выходным до 20:00."
        ),
    )

    private val _feedbacks = MutableLiveData<List<FeedbackModel>>(emptyList())

    /**
     * Возвращает список обратной связи.
     */
    val feedbacks: LiveData<List<FeedbackModel>> = _feedbacks

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _feedbacks.postValue(feedbacksFake)
            _isLoading.postValue(false)
        }
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