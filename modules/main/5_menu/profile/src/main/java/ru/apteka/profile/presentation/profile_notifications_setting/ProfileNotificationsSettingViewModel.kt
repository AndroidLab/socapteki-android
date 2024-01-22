package ru.apteka.profile.presentation.profile_notifications_setting

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Настройка уведомлений".
 */
@HiltViewModel
class ProfileNotificationsSettingViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private var _notificationsOnlineOrderStatusMail = false
    private var _notificationsStocksMail = false
    private var _notificationsStocksSms = false
    private var _notificationsStocksPush = false
    private var _notificationsPointsSmsOrMail = false
    private var _notificationsPointsPush = false

    /**
     * Возвращает или устанавливает флаг 'Статус интернет-заказа, получать уведомления на e-mail'.
     */
    val notificationsOnlineOrderStatusMail = MutableLiveData(_notificationsOnlineOrderStatusMail)


    /**
     * Возвращает или устанавливает флаг 'Акции, получать уведомления на e-mail'.
     */
    val notificationsStocksMail = MutableLiveData(_notificationsStocksMail)

    /**
     * Возвращает или устанавливает флаг 'Акции, смс уведомления'.
     */
    val notificationsStocksSms = MutableLiveData(_notificationsStocksSms)

    /**
     * Возвращает или устанавливает флаг 'Акции, push уведомления'.
     */
    val notificationsStocksPush = MutableLiveData(_notificationsStocksPush)


    /**
     * Возвращает или устанавливает флаг 'Баллы, смс или mail уведомления'.
     */
    val notificationsPointsSmsOrMail = MutableLiveData(_notificationsPointsSmsOrMail)

    /**
     * Возвращает или устанавливает флаг 'Баллы, push уведомления'.
     */
    val notificationsPointsPush = MutableLiveData(_notificationsPointsPush)


    /**
     * Возвращает флаг изменений.
     */
    val isChanged = MediatorLiveData<Boolean>().apply {
        fun checkChanged() {
            postValue(
                true//!isLoading.value!! && (smsNotice.value!! != _smsNotice || mailNotice.value!! != _mailNotice)
            )
        }

        addSource(notificationsOnlineOrderStatusMail) {
            checkChanged()
        }
        addSource(notificationsStocksMail) {
            checkChanged()
        }
        addSource(notificationsStocksSms) {
            checkChanged()
        }
        addSource(notificationsStocksPush) {
            checkChanged()
        }
        addSource(notificationsPointsSmsOrMail) {
            checkChanged()
        }
        addSource(notificationsPointsPush) {
            checkChanged()
        }
        addSource(isLoading) {
            checkChanged()
        }
    }

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _notificationsOnlineOrderStatusMail = true
            _notificationsStocksMail = true
            _notificationsStocksSms = true
            _notificationsStocksPush = true
            _notificationsPointsSmsOrMail = true
            _notificationsPointsPush = false

            notificationsOnlineOrderStatusMail.postValue(_notificationsOnlineOrderStatusMail)
            notificationsStocksMail.postValue(_notificationsStocksMail)
            notificationsStocksSms.postValue(_notificationsStocksSms)
            notificationsStocksPush.postValue(_notificationsStocksPush)
            notificationsPointsSmsOrMail.postValue(_notificationsPointsSmsOrMail)
            notificationsPointsPush.postValue(_notificationsPointsPush)

            _isLoading.postValue(false)
        }
    }


    /**
     * Сохранить.
     */
    fun save(success: () -> Unit) {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            mainThread {
                success()
            }
            _isLoading.postValue(false)
        }
    }

}