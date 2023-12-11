package ru.apteka.orders.presentation.order_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.OrderDetailsModel
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.repository.orders.OrdersRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject
import kotlin.properties.Delegates


/**
 * Представляет модель представления "Детали заказа".
 */
@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val ordersRepository: OrdersRepository,
    private val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращает или устанавливает заказ.
     */
    var order by Delegates.observable<OrderModel?>(null) { d, old, new ->
        getOrderDetail(new!!)
    }

    private val _orderDetail = MutableLiveData<OrderDetailsModel?>(null)

    /**
     * Возвращает детали заказа.
     */
    val orderDetail: LiveData<OrderDetailsModel?> = _orderDetail

    private fun getOrderDetail(order: OrderModel) {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { ordersRepository.getOrderDetails(order) },
                onSuccess = { orderDetail ->
                    _orderDetail.postValue(orderDetail)
                },
                isLoading = _isLoading
            )
        }
    }


}