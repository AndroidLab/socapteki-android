package ru.apteka.feedback.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.components.data.utils.validateEmail
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Обратная свяь".
 */
@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: MessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает или устанавливает ФИО.
     */
    val fio = MutableLiveData("")

    /**
     * Возвращает или устанавливает почту.
     */
    val email = MutableLiveData("")

    /**
     * Возвращает ошибку валидации.
     */
    val isEmailValid = email.map {
        if (validateEmail(email.value!!)) null else R.string.email_not_valid
    }

    /**
     * Возвращает список причин.
     */
    val reasons = listOf(
        "Тема обращения 1",
        "Тема обращения 2",
        "Тема обращения 3",
        "Тема обращения 4",
        "Тема обращения 5",
    )

    private val _reason: MutableLiveData<String?> = MutableLiveData<String?>(null)

    /**
     * Возвращает выбранную причину.
     */
    val reason: LiveData<String?> = _reason

    /**
     * Возвращает выбранную причину.
     */
    fun onReasonSelect(position: Int) {
        _reason.value = reasons[position]
    }

    /**
     * Возвращает или устанавливает текст обращения.
     */
    val message = MutableLiveData("")

    /**
     * Возвращает или устанавливает флаг согласия на обработку перс. данных.
     */
    val isPersonalDataChecked = MutableLiveData(false)

    /**
     * Возвращает флаг доступности отправки сообщения.
     */
    val isMessageSendEnabled = MediatorLiveData<Boolean>().apply {
        fun checkFieldsFilled() {
            value = fio.value!!.isNotEmpty() && email.value!!.isNotEmpty()
                    && isEmailValid.value == null && reason.value != null
                    && reason.value!!.isNotEmpty() && message.value!!.isNotEmpty()
                    && isPersonalDataChecked.value!! && !isLoading.value!!
        }

        addSource(fio) {
            checkFieldsFilled()
        }

        addSource(email) {
            checkFieldsFilled()
        }

        addSource(reason) {
            checkFieldsFilled()
        }

        addSource(message) {
            checkFieldsFilled()
        }

        addSource(isPersonalDataChecked) {
            checkFieldsFilled()
        }

        addSource(isLoading) {
            checkFieldsFilled()
        }
    }

    /**
     * Возвращает флаг успешной отправки сообщения.
     */
    val isMessageSendSuccess = SingleLiveEvent<Unit>()

    /**
     * Отправляет сообщение.
     */
    fun sendMessage() {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            mainThread {
                isMessageSendSuccess.call()
            }
            _isLoading.postValue(false)
        }
    }

}