package ru.apteka.notifications.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.Label
import ru.apteka.components.data.models.OrderDeliveryMethod
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderPayStatus
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.StockModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.getProductsFake2
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
            title = "Доставка, 4 559,90 ₽",
            desc = "2-я Владимирская ул. 29А, Москва",
            type = NotificationModel.NotificationType.ORDERS,
            orderOrStock = OrderModel(
                number = 123,
                sum = "4 559,90 ₽",
                status = OrderStatus.NEW,
                date = 1697711146,
                payStatus = OrderPayStatus.SUCCESS,
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            )
        ),
        NotificationModel(
            title = "Доставка, 4 559,90 ₽",
            desc = "2-я Владимирская ул. 29А, Москва",
            type = NotificationModel.NotificationType.ORDERS,
            orderOrStock = OrderModel(
                number = 12345,
                status = OrderStatus.NEW,
                date = 1697624746,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            )
        ),
        NotificationModel(
            title = "Скидки до 45% на товары для красотыy",
            desc = "01 сентября 2023 - 30 сентября 2023",
            type = NotificationModel.NotificationType.STOCKS,
            orderOrStock = StockModel(
                imageSrc = "",
                title = "Скидки до 45% на товары для красоты",
                date = "01 сентября 2023 - 30 сентября 2023",
                description = "Только с 1 сентября по 30 сентября 2023г. специальное предложение на товары для красоты - скидка до 45 %!Натуральный препарат с экстрактом французского артишока улучшает отток желчи и помогает защитить печень.\n" +
                        "Кому может подойти средство:\n" +
                        "детям с диагнозом \"дискенезия желчевыводящих путей\" и частыми запорами. Раствор разрешен даже младенцам под наблюдением врача. Таблетки можно использовать от 6 лет.\n" +
                        "взрослым с нарушением питания: частым употреблением фастфуда и спиртных напитков\n" +
                        "взрослым с проблемной кожей, если причина высыпаний внутри\n" +
                        "взрослым, принимающим большое количество медикаментов\n" +
                        "Хофитол в растворе или таблетках со скидкой до 20% заказывайте здесь! Количество товаров ограничено.",
                labels = listOf(Label.ADVERT),
            ),
        ),
        NotificationModel(
            title = "Скидки до 40% на товары для красоты",
            desc = "01 сентября 2023 - 30 сентября 2023",
            type = NotificationModel.NotificationType.STOCKS,
            orderOrStock = StockModel(
                imageSrc = "",
                title = "Скидки до 40% на товары для красоты",
                date = "01 сентября 2023 - 30 сентября 2023",
                description = "Только с 1 сентября по 30 сентября 2023г. специальное предложение на товары для красоты - скидка до 45 %!Натуральный препарат с экстрактом французского артишока улучшает отток желчи и помогает защитить печень.\n" +
                        "Кому может подойти средство:\n" +
                        "детям с диагнозом \"дискенезия желчевыводящих путей\" и частыми запорами. Раствор разрешен даже младенцам под наблюдением врача. Таблетки можно использовать от 6 лет.\n" +
                        "взрослым с нарушением питания: частым употреблением фастфуда и спиртных напитков\n" +
                        "взрослым с проблемной кожей, если причина высыпаний внутри\n" +
                        "взрослым, принимающим большое количество медикаментов\n" +
                        "Хофитол в растворе или таблетках со скидкой до 20% заказывайте здесь! Количество товаров ограничено.",
                labels = listOf(Label.ADVERT),
            ),
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
