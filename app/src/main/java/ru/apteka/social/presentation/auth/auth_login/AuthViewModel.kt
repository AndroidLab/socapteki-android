package ru.apteka.social.presentation.auth.auth_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.common.data.RequestHandler
import ru.apteka.common.data.utils.launchIO
import ru.apteka.common.data.utils.mainThread
import ru.apteka.common.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.common.ui.BaseViewModel
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.social.domain.login.usecase.SendPhoneUseCase
import javax.inject.Inject


/**
 * Представляет модель представления "Авторизации".
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val sendPhoneUseCase: SendPhoneUseCase,
    private val accountsPreferences: AccountsPreferences
) : BaseViewModel() {

    /**
     * Устанавливает или возвращает номер телефона.
     */
    val phoneNumber = MutableLiveData("")

    /**
     * Возвращает номер телефона без маски.
     */
    val phoneNumberRaw = phoneNumber.map {   //TODO сделать от маски.
        _isError.value = false
        it
            .replace("+7", "")
            .replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace(" ", "")
    }

    /**
     * Возвращает или устанавливает флаг 'Запомнить меня'.
     */
    val isRememberMeLiveData = MutableLiveData(false)

    /**
     * Возвращает или устанавливает флаг 'Обработку персональных данных'.
     */
    val isPersonalDataLiveData = MutableLiveData(false)

    private val _isError = MutableLiveData(false)

    /**
     * Возвращает флаг ошиибки отправки номера.
     */
    val isError: LiveData<Boolean> = _isError

    /**
     * Возвращает событие навигации к подтверждению кода.
     */
    val isNavigationToConfirmCode = SingleLiveEvent<Unit>()

    /**
     * Отправляет телефонный номер.
     */
    fun sendPhoneNumber() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { sendPhoneUseCase.execute(phoneNumberRaw.value!!) },
                onSuccess = {
                    mainThread {
                        if (it.success) {
                            isNavigationToConfirmCode.call()
                        } else {
                            _isError.value = true
                        }
                    }
                },
                isLoading = _isLoading
            )
        }
    }

}