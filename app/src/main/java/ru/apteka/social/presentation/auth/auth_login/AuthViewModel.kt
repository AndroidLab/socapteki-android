package ru.apteka.social.presentation.auth.auth_login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Авторизации".
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Устанавливает или возвращает номер телефона.
     */
    val phoneNumber = MutableLiveData("")

    /**
     * Возвращает номер телефона без маски.
     */
    val phoneNumberRaw = phoneNumber.map {
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

}