package ru.apteka.product_card.presentation.product_card_write_review

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.validateEmail
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject

/**
 * Представляет модель представления "Карточка товара, написать отзыв".
 */
@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает продукцию.
     */
    val product = MutableLiveData<ProductModel?>(null)

    /**
     * Возвращает или устанавливает рэйтинг.
     */
    val rating = MutableLiveData(0f)

    /**
     * Возвращает или устанавливает ФИО.
     */
    val fio = MutableLiveData("")

    /**
     * Возвращает или устанавливает майл.
     */
    val mail = MutableLiveData("")

    /**
     * Возвращает ошибку валидации майла.
     */
    val isMailValid = ScopedLiveData<ViewModel, Int?>(null)

    /**
     * Возвращает или устанавливает отзыв.
     */
    val comment = MutableLiveData("")

    /**
     * Возвращает флаг доступности отправки отзыва.
     */
    val canSendReview = MediatorLiveData<Boolean>().apply {
        fun checkDataFilled() {
            postValue(
                isLoading.value == false &&
                        rating.value!! > 0 &&
                        fio.value!!.isNotEmpty() &&
                        mail.value!!.isNotEmpty() &&
                        comment.value!!.isNotEmpty() &&
                        isMailValid.value == null
            )
        }

        addSource(isLoading) {
            checkDataFilled()
        }

        addSource(rating) {
            checkDataFilled()
        }

        addSource(fio) {
            checkDataFilled()
        }

        addSource(mail) {
            isMailValid.setValue(null)
            checkDataFilled()
        }

        addSource(comment) {
            checkDataFilled()
        }

        addSource(isMailValid) {
            checkDataFilled()
        }
    }

    /**
     * Отправляет отзыв.
     */
    fun sendReview() {
        if (validateEmail(mail.value!!)) {
            viewModelScope.launchIO {
                isLoading.postValue(true)
                delay(1500)
                mainThread {
                    navigationManager.generalNavController.popBackStack()
                }
                isLoading.postValue(false)
            }
        } else {
            isMailValid.setValue(R.string.email_not_valid)
        }
    }
}
