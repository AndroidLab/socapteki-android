package ru.apteka.profile.presentation.setting_notifications

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.SubscriptionsModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Настройка уведомлений".
 */
@HiltViewModel
class SettingNotificationsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : MainScreenBaseViewModel(
    navigationManager,
    messageNoticeService
) {
    private var _smsNotice = false
    private var _mailNotice = false

    /**
     * Возвращает или устанавливает флаг 'Смс уведомления'.
     */
    val smsNotice = MutableLiveData(_smsNotice)

    /**
     * Возвращает или устанавливает флаг 'Maqk уведомления'.
     */
    val mailNotice = MutableLiveData(_mailNotice)

    /**
     * Возвращает флаг изменений.
     */
    val isChanged = MediatorLiveData<Boolean>().apply {
        fun checkChanged() {
            postValue(
                !isLoading.value!! && (smsNotice.value!! != _smsNotice || mailNotice.value!! != _mailNotice)
            )
        }

        addSource(smsNotice) {
            checkChanged()
        }
        addSource(mailNotice) {
            checkChanged()
        }
        addSource(isLoading) {
            checkChanged()
        }
    }

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { loginRepository.getSubscriptions() },
                onSuccess = { subscriptions ->
                    mainThread {
                        _smsNotice = subscriptions.sms
                        _mailNotice = subscriptions.mail
                        smsNotice.value = _smsNotice
                        mailNotice.value = _mailNotice
                    }
                },
                isLoading = _isLoading
            )
        }
    }


    /**
     * Клик по смс уведомлениям.
     */
    fun clickSmsNotice() {
        smsNotice.value = !smsNotice.value!!
    }

    /**
     * Клик по емайл уведомлениям.
     */
    fun clickMailNotice() {
        mailNotice.value = !mailNotice.value!!
    }

    /**
     * Сохранить.
     */
    fun save() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.saveSubscriptions(
                        SubscriptionsModel(
                            smsNotice.value!!,
                            mailNotice.value!!
                        )
                    )
                },
                onSuccess = {
                    _smsNotice = smsNotice.value!!
                    _mailNotice = mailNotice.value!!
                },
                isLoading = _isLoading
            )
        }
    }

}