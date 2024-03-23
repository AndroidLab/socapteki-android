package ru.apteka.profile.presentation.profile_personal_data_mail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.ConfirmationCodeModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.validateEmail
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject

/**
 * Представляет модель представления "Персональные данныеб ищменить почту".
 */
@HiltViewModel
class PersonalDataMailViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    messageService: IMessageService,
    navigationManager: NavigationManager,
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает или устанавливает адрес почты.
     */
    val mail = MutableLiveData("")

    /**
     * Возвращает флаг ошибки формата почты.
     */
    val isMailFormatValid = ScopedLiveData(true)

    /**
     * Возвращает модель подтверждения кода.
     */
    val confirmationCode = ConfirmationCodeModel(
        loginRepository = loginRepository,
        requestHandler = requestHandler,
        scope = viewModelScope,
        getPhoneRaw = {
            mail.value!!
        },
    )

    init {
        viewModelScope.launchIO {
            mail.asFlow().collect {
                isMailFormatValid.postValue(true)
            }
        }
    }

    /**
     * Подтверждение кодом.
     */
    fun confirmCode() {
        if (validateEmail(mail.value!!)) {
            confirmationCode.requestCode()
        } else {
            isMailFormatValid.setValue(false)
        }
    }

    /**
     * Сохраняет персональные данные.
     */
    fun savePersonalData(success: () -> Unit) {
        viewModelScope.launchIO {
            confirmationCode.confirmCode(
                request = { code ->
                    loginRepository.savePersonalDataMail(
                        mail.value!!
                    )
                },
                success = success
            )
        }
    }
}
