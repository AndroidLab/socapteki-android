package ru.apteka.orders.presentation.orders

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.OrderDeliveryMethod
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderPayStatus
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.orders.data.repository.OrdersRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.getProductsFake2
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.orders.data.models.OrderFilterModel
import ru.apteka.orders.data.services.OrdersPreferences
import javax.inject.Inject


/**
 * Представляет модель представления "Заказы".
 */
@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val ordersRepository: OrdersRepository,
    private val ordersPreferences: OrdersPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService,
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Получает заказы.
     */
    suspend fun getOrdersFake(): List<OrderModel> {
        return listOf(
            OrderModel(
                number = 123,
                status = OrderStatus.NEW,
                date = 1697711146,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            ),
            OrderModel(
                number = 12345,
                status = OrderStatus.NEW,
                date = 1697624746,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            ),
            OrderModel(
                number = 123456,
                status = OrderStatus.IN_PROCESSING,
                date = 1677711395,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            ),
            OrderModel(
                number = 1234567,
                status = OrderStatus.IN_PROCESSING,
                date = 1680389795,
                payStatus = OrderPayStatus.FAILED,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.PICKUP,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Наличными",
                products = getProductsFake2(),
                canceledTime = "00:12:52"
            ),
            OrderModel(
                number = 12345678,
                status = OrderStatus.CANCELED,
                date = 1682981795,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.PICKUP,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Наличными",
                products = getProductsFake2(),
                canceledReason = "Нет у поставщиков\nК сожалению, ваш заказ был отменен, но мы можем собрать его снова"
            ),
            OrderModel(
                number = 123456789,
                status = OrderStatus.AWAITING_PAYMENT,
                date = 1685660195,
                payStatus = OrderPayStatus.FAILED,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.PICKUP,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Наличными",
                products = getProductsFake2(),
                canceledTime = "00:12:52"
            )
        )
    }

    /**
     * Возвращат модель фильтра.
     */
    val orderFilter = OrderFilterModel(
        _items = listOf(
            OrderFilterModel.Item(
                status = null
            ),
            OrderFilterModel.Item(
                status = OrderStatus.NEW
            ),
            OrderFilterModel.Item(
                status = OrderStatus.READY_TO_RECEIVE
            ),
            OrderFilterModel.Item(
                status = OrderStatus.TRANSFERRED_TO_COURIER
            ),
            OrderFilterModel.Item(
                status = OrderStatus.DELIVERY_POSTPONED
            ),
            OrderFilterModel.Item(
                status = OrderStatus.RECEIVED
            ),
            OrderFilterModel.Item(
                status = OrderStatus.CANCELED
            ),
            OrderFilterModel.Item(
                status = OrderStatus.AWAITING_PAYMENT
            ),
        )
    ) {
        //ordersPreferences.orderFilter = it.status
    }.apply {
        items[0].isItemSelected.value = true
    }


    private val _orders = MutableLiveData<List<OrderModel>>(emptyList())

    /**
     * Возвращает список всех заказов.
     */
    val orders: MutableLiveData<List<OrderModel>> = _orders

    /**
     * Возвращает фильтрованный список заказов.
     */
    val ordersFiltered = MediatorLiveData<List<OrderModel>>().apply {

        fun filterOrders() {
            postValue(
                when (orderFilter.selectedItem.value?.status) {
                    OrderStatus.NEW -> _orders.value!!.filter { it.status == OrderStatus.NEW }
                    OrderStatus.READY_TO_RECEIVE -> _orders.value!!.filter { it.status == OrderStatus.READY_TO_RECEIVE }
                    OrderStatus.TRANSFERRED_TO_COURIER -> _orders.value!!.filter { it.status == OrderStatus.TRANSFERRED_TO_COURIER }
                    OrderStatus.DELIVERY_POSTPONED -> _orders.value!!.filter { it.status == OrderStatus.DELIVERY_POSTPONED }
                    OrderStatus.RECEIVED -> _orders.value!!.filter { it.status == OrderStatus.RECEIVED }
                    OrderStatus.CANCELED -> _orders.value!!.filter { it.status == OrderStatus.CANCELED }
                    OrderStatus.AWAITING_PAYMENT -> _orders.value!!.filter { it.status == OrderStatus.AWAITING_PAYMENT }
                    else -> _orders.value
                }?.sortedByDescending { it.date }
            )
        }

        addSource(_orders) {
            filterOrders()
        }

        addSource(orderFilter.selectedItem) {
            filterOrders()
        }

        /*orderFilter.items.forEach {
            addSource(it.isItemSelected) {
                filterOrders()
            }
        }*/
    }

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _orders.postValue(getOrdersFake()!!)
            _isLoading.postValue(false)
        }
    }

}