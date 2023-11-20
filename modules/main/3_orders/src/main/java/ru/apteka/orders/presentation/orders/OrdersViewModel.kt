package ru.apteka.orders.presentation.orders

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.repository.orders.OrdersRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import ru.apteka.orders.data.models.OrderFilter
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
    messageNoticeService: IMessageNoticeService,
) : MainScreenBaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращат модель фильтра.
     */
    val orderFilter = OrderFilterModel(
        _items = listOf(
            OrderFilterModel.Item(
                status = OrderFilter.ALL
            ),
            OrderFilterModel.Item(
                status = OrderFilter.IN_WORK
            ),
            OrderFilterModel.Item(
                status = OrderFilter.CANCELED
            ),
            OrderFilterModel.Item(
                status = OrderFilter.COMPLETED
            ),
        )
    ) {
        ordersPreferences.orderFilter = it.status
    }.apply {
        setItemSelected(0)
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
                when (ordersPreferences.orderFilter) {
                    OrderFilter.ALL -> _orders.value
                    OrderFilter.IN_WORK -> _orders.value!!.filter { it.status != OrderStatus.CANCELED && it.status != OrderStatus.RECEIVED}
                    OrderFilter.CANCELED -> _orders.value!!.filter { it.status == OrderStatus.CANCELED }
                    OrderFilter.COMPLETED -> _orders.value!!.filter { it.status == OrderStatus.RECEIVED }
                }?.sortedByDescending { it.date }
            )
        }

        addSource(_orders) {
            filterOrders()
        }

        addSource(ordersPreferences.orderFilterFlow.asLiveData()) {
            filterOrders()
        }

        orderFilter.items.forEach {
            addSource(it.isItemSelected) {
                filterOrders()
            }
        }
    }

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { ordersRepository.getOrders() },
                onSuccess = { orders ->
                    _orders.postValue(orders)
                },
                isLoading = _isLoading
            )
        }
    }


}