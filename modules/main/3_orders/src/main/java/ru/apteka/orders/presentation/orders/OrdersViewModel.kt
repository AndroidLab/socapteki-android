package ru.apteka.orders.presentation.orders

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.repository.orders.OrdersRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import ru.apteka.orders.data.OrderFilterModel
import javax.inject.Inject


/**
 * Представляет модель представления "Заказы".
 */
@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val ordersRepository: OrdersRepository,
    private val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService,
    @ApplicationContext context: Context
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
                status = OrderStatus.ALL
            ),
            OrderFilterModel.Item(
                status = OrderStatus.IN_WORK
            ),
            OrderFilterModel.Item(
                status = OrderStatus.CANCELED
            ),
            OrderFilterModel.Item(
                status = OrderStatus.COMPLETED
            ),
        )
    ) {
        userPreferences.orderFilter = it.status
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
                when(userPreferences.orderFilter) {
                    OrderStatus.ALL -> _orders.value
                    OrderStatus.IN_WORK -> _orders.value!!.filter { it.status == OrderStatus.IN_WORK }
                    OrderStatus.CANCELED -> _orders.value!!.filter { it.status == OrderStatus.CANCELED }
                    OrderStatus.COMPLETED -> _orders.value!!.filter { it.status == OrderStatus.COMPLETED }
                }?.sortedByDescending { it.date }
            )
        }

        addSource(_orders) {
            filterOrders()
        }

        addSource(userPreferences.orderFilterFlow.asLiveData()) {
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