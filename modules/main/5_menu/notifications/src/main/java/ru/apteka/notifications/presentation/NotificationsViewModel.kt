package ru.apteka.notifications.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.notifications.data.model.NotificationFilter
import ru.apteka.notifications.data.model.NotificationFilterModel
import ru.apteka.notifications.data.model.NotificationModel
import javax.inject.Inject

/**
 * Представляет модель представления "Уведомления".
 */
@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val fakeNotifications = listOf(
        NotificationModel(
            title = "«Социальная Аптека»",
            desc = "Supporting line text lorem ipsum dolor sit amet, consectetur",
            type = NotificationModel.NotificationType.ORDERS
        ),
        NotificationModel(
            title = "«Социальная Аптека»",
            desc = "Заказ №507505552 готов и ожидает вас еще 2 дн. Справка 8 800 200 9001",
            type = NotificationModel.NotificationType.ORDERS
        ),
        NotificationModel(
            title = "-20% на Johnsons Baby",
            desc = "Supporting line text lorem ipsum dolor sit amet, consectetur",
            type = NotificationModel.NotificationType.STOCKS
        ),
        NotificationModel(
            title = "Эссенциале  по акции 2+1",
            desc = "Supporting line text lorem ipsum dolor sit amet, consectetur",
            type = NotificationModel.NotificationType.STOCKS
        )
    )

    private val _notificationFilter = MutableLiveData<NotificationFilterModel?>(null)

    /**
     *
     */
    val notificationFilter: LiveData<NotificationFilterModel?> = _notificationFilter

    private val selectedNotificationFilter = MutableLiveData<NotificationFilterModel.Item?>(null)

    private val _notifications = MutableLiveData<List<NotificationModel>>(emptyList())

    /**
     *
     */
    val notifications: LiveData<List<NotificationModel>> = _notifications

    // private val _notificationFiltered = MutableLiveData<List<NotificationModel>>(emptyList())

    /**
     *
     */
    val notificationFiltered = MediatorLiveData<List<NotificationModel>>().apply {
        fun filterNotifications() {
            value = when (selectedNotificationFilter.value?.status) {
                NotificationFilter.ALL -> _notifications.value
                NotificationFilter.ORDERS -> _notifications.value!!.filter { it.type == NotificationModel.NotificationType.ORDERS }
                NotificationFilter.STOCKS -> _notifications.value!!.filter { it.type == NotificationModel.NotificationType.STOCKS }
                else -> _notifications.value
            } // ?.sortedByDescending { it.date }
        }

        addSource(notifications) {
            filterNotifications()
        }

        addSource(selectedNotificationFilter) {
            filterNotifications()
        }
    }

    /**
     * Возвращает флаг прочтения всех уведомлений.
     */
    val isAllNotificationRead = MediatorLiveData<Boolean>()

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _notifications.postValue(fakeNotifications)
            _isLoading.postValue(false)

            mainThread {
                _notificationFilter.value = NotificationFilterModel(
                    _items = listOf(
                        NotificationFilterModel.Item(
                            status = NotificationFilter.ALL,
                            count = _notifications.value!!.size
                        ),
                        NotificationFilterModel.Item(
                            status = NotificationFilter.ORDERS,
                            count = _notifications.value!!.filter { it.type == NotificationModel.NotificationType.ORDERS }.size
                        ),
                        NotificationFilterModel.Item(
                            status = NotificationFilter.STOCKS,
                            count = _notifications.value!!.filter { it.type == NotificationModel.NotificationType.STOCKS }.size
                        ),
                    )
                ) {
                    // ordersPreferences.orderFilter = it.status
                    selectedNotificationFilter.value = it
                }.apply {
                    setItemSelected(0)
                }

                isAllNotificationRead.apply {
                    fun checkNotificationsRead() {
                        postValue(
                            _notifications.value!!.all { it.isRead.value!! }
                        )
                    }
                    _notifications.value!!.forEach {
                        addSource(it.isRead) {
                            checkNotificationsRead()
                        }
                    }
                }
            }
        }
    }
}
