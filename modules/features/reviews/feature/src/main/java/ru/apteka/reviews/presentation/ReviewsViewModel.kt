package ru.apteka.reviews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ReviewItem
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Отзывы".
 */
@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: MessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    private val reviewsFake = listOf(
        ReviewItem(
            name = "Пескова Наталья 0",
            rating = 3.3f,
            date = 1698920656,
            text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
        ),
        ReviewItem(
            name = "Пескова Наталья 1",
            rating = 2.3f,
            date = 1698920656,
            text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
        ),
        ReviewItem(
            name = "Пескова Наталья 2",
            rating = 1.3f,
            date = 1698920656,
            text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
        ),
        ReviewItem(
            name = "Пескова Наталья 3",
            rating = 2.3f,
            date = 1698920656,
            text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
        ),
        ReviewItem(
            name = "Пескова Наталья 4",
            rating = 1.3f,
            date = 1698920656,
            text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
        ),
        ReviewItem(
            name = "Пескова Наталья 4",
            rating = 1.3f,
            date = 1698920656,
            text = "Супер пробиотик, действительно помогает. Лучший из подобных препаратов"
        ),
    )

    private val _reviews = MutableLiveData<List<ReviewItem>>(emptyList())

    /**
     * Возвращает отзывы.
     */
    val reviews: LiveData<List<ReviewItem>> = _reviews

    init {
        getMoreReviews()
    }

    /**
     * Возвращает отзывы.
     */
    fun getMoreReviews() {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            requestHandler.handleApiRequest(
                onRequest = { _getReviews() },
                onSuccess = { reviews ->
                    _reviews.postValue(
                        _reviews.value!! + reviews
                    )
                    _isLoading.postValue(false)
                },
                //isLoading = _isLoading
            )
        }
    }

    private suspend fun _getReviews(): List<ReviewItem> {
        return reviewsFake
    }

}