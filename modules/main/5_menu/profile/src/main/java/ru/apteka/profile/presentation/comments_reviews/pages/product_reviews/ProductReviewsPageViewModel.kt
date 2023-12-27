package ru.apteka.profile.presentation.comments_reviews.pages.product_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.profile.data.models.CommentModel
import javax.inject.Inject


/**
 * Представляет модель представления "Страница комментариев".
 */
@HiltViewModel
class ProductReviewsPageViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    private val commentsFake = listOf(
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            text = "По моему диклофенак - акос лучший гель при боли в суставах. Те средства, что я раньше использовала и рядом не стоят. При чем этот гель стоит дешевле, да еще и аромат у него приятный.\nПо моему диклофенак - акос лучший гель при боли в суставах. Те средства, что я раньше использовала и рядом не стоят. При чем этот гель стоит дешевле, да еще и аромат у него приятный.",
            answers = listOf(
                "Спасибо! Рады слышать",
                "Хорошая текстура 1",
                "Хорошая текстура 2"
            ),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            text = "По моему диклофенак - акос лучший гель при боли в суставах. Те средства, что я раньше использовала и рядом не стоят. При чем этот гель стоит дешевле, да еще и аромат у него приятный.",
            answers = listOf(
                "Спасибо! Рады слышать",
                "Хорошая текстура 1",
            ),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            text = "По моему диклофенак - акос лучший гель при боли в суставах. Те средства, что я раньше использовала и рядом не стоят. При чем этот гель стоит дешевле, да еще и аромат у него приятный.",
            answers = listOf(
                "Спасибо! Рады слышать",
            ),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            text = "По моему диклофенак - акос лучший гель при боли в суставах. Те средства, что я раньше использовала и рядом не стоят. При чем этот гель стоит дешевле, да еще и аромат у него приятный.",
            answers = listOf(),
        ),
        CommentModel(
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            title = "Диклофенак-акос гель для наружного применения 5% 50 г",
            text = "По моему диклофенак - акос лучший гель при боли в суставах. Те средства, что я раньше использовала и рядом не стоят. При чем этот гель стоит дешевле, да еще и аромат у него приятный.",
            answers = listOf(),
        ),
    )

    private val _comments = MutableLiveData<List<CommentModel>>(emptyList())

    /**
     * Возвращет список комментариев.
     */
    val comments: LiveData<List<CommentModel>> = _comments

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _comments.postValue(commentsFake)
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