package ru.apteka.social.presentation.auth.auth_login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.PhoneInputModel
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
     * Возвращает модель поля ввода номера телефона.
     */
    val phoneInput = PhoneInputModel()

    /**
     * Возвращает или устанавливает флаг 'Политика конфидециальности'.
     */
    val isPrivacyPolicy = MutableLiveData(false)

    /**
     * Возвращает или устанавливает флаг 'Поучение новостей'.
     */
    val isAdvertNews = MutableLiveData(false)

    /**
     * Возвращает флаг доступности кнопки подтверждения номера.
     */
    val isPhoneBtnConfirmEnabled = MediatorLiveData<Boolean>().apply {
        fun checkFilledData() {
            postValue(
                phoneInput.getPhoneRaw().length == 10 && isPrivacyPolicy.value!! && isAdvertNews.value!!
            )
        }

        addSource(phoneInput.phone) {
            checkFilledData()
        }

        addSource(isPrivacyPolicy) {
            checkFilledData()
        }
        addSource(isAdvertNews) {
            checkFilledData()
        }
    }

}