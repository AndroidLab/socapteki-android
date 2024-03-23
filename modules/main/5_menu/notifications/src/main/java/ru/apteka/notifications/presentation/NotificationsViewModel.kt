package ru.apteka.notifications.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
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

    /**
     *
     */
    val notificationFilter = ScopedLiveData<ViewModel, NotificationFilterModel?>(null)

    private val selectedNotificationFilter = MutableLiveData<NotificationFilterModel.Item?>(null)

    /**
     *
     */
    val notifications = ScopedLiveData(emptyList<NotificationModel>())

    // private val _notificationFiltered = MutableLiveData<List<NotificationModel>>(emptyList())

    /**
     *
     */
    val notificationFiltered = MediatorLiveData<List<NotificationModel>>().apply {
        fun filterNotifications() {
            value = when (selectedNotificationFilter.value?.status) {
                NotificationFilter.ALL -> notifications.value
                NotificationFilter.ORDERS -> notifications.value!!.filter { it.type == NotificationModel.NotificationType.ORDERS }
                NotificationFilter.STOCKS -> notifications.value!!.filter { it.type == NotificationModel.NotificationType.STOCKS }
                else -> notifications.value
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
            isLoading.postValue(true)
            delay(1500)
            notifications.postValue(fakeNotifications)
            isLoading.postValue(false)

            mainThread {
                notificationFilter.setValue(NotificationFilterModel(
                    _items = listOf(
                        NotificationFilterModel.Item(
                            status = NotificationFilter.ALL,
                            count = notifications.value!!.size
                        ),
                        NotificationFilterModel.Item(
                            status = NotificationFilter.ORDERS,
                            count = notifications.value!!.filter { it.type == NotificationModel.NotificationType.ORDERS }.size
                        ),
                        NotificationFilterModel.Item(
                            status = NotificationFilter.STOCKS,
                            count = notifications.value!!.filter { it.type == NotificationModel.NotificationType.STOCKS }.size
                        ),
                    )
                ) {
                    // ordersPreferences.orderFilter = it.status
                    selectedNotificationFilter.value = it
                }.apply {
                    setItemSelected(0)
                })

                isAllNotificationRead.apply {
                    fun checkNotificationsRead() {
                        postValue(
                            notifications.value!!.all { it.isRead.value!! }
                        )
                    }
                    notifications.value!!.forEach {
                        addSource(it.isRead) {
                            checkNotificationsRead()
                        }
                    }
                }
            }
        }
    }
}
