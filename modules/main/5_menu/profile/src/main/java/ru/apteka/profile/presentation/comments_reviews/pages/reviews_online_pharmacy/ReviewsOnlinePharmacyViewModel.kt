package ru.apteka.profile.presentation.comments_reviews.pages.reviews_online_pharmacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.profile.data.models.CommentModel
import javax.inject.Inject


/**
 * Представляет модель представления "Страница отзывы об интернет-аптеке".
 */
@HiltViewModel
class ReviewsOnlinePharmacyViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    //<font color="#00A95D">«Health»</font>
    private val commentsFake = listOf(
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "<font color=\"#2E2C34\">Аптека «Социальная аптека»</font><br><font color=\"#667085\">Москва, Варшавское шоссе, 143а</font>",
            text = "Хороший сервис, быстрая доставка. Не возникло проблем. Спасибо",
            answers = listOf(
                "Спасибо! Рады слышать",
                "Хорошая текстура 1",
                "Хорошая текстура 2"
            ),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "<font color=\"#2E2C34\">Аптека «Социальная аптека»</font><br><font color=\"#667085\">Москва, Варшавское шоссе, 143а</font>",
            text = "Хороший сервис, быстрая доставка. Не возникло проблем. Спасибо",
            answers = listOf(
                "Спасибо! Рады слышать",
                "Хорошая текстура 1",
            ),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "<font color=\"#2E2C34\">Аптека «Социальная аптека»</font><br><font color=\"#667085\">Москва, Варшавское шоссе, 143а</font>",
            text = "Хороший сервис, быстрая доставка. Не возникло проблем. Спасибо",
            answers = listOf(
                "Спасибо! Рады слышать",
            ),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "<font color=\"#2E2C34\">Аптека «Социальная аптека»</font><br><font color=\"#667085\">Москва, Варшавское шоссе, 143а</font>",
            text = "Хороший сервис, быстрая доставка. Не возникло проблем. Спасибо",
            answers = listOf(),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "<font color=\"#2E2C34\">Аптека «Социальная аптека»</font><br><font color=\"#667085\">Москва, Варшавское шоссе, 143а</font>",
            text = "Хороший сервис, быстрая доставка. Не возникло проблем. Спасибо",
            answers = listOf(),
        ),
    )

    /**
     * Возвращет список комментариев.
     */
    val comments = ScopedLiveData<ViewModel, List<CommentModel>>(emptyList())

    init {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            comments.postValue(commentsFake)
            isLoading.postValue(false)
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