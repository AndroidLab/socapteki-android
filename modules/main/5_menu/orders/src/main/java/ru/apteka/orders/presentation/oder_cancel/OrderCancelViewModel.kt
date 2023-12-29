package ru.apteka.orders.presentation.oder_cancel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.OrderModel
import ru.apteka.orders.data.repository.OrdersRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.orders.R
import ru.apteka.orders.data.models.OrderCancelModel
import javax.inject.Inject


/**
 * Представляет модель представления "Детали заказа".
 */
@HiltViewModel
class OrderCancelViewModel @Inject constructor(
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
    val order = MutableLiveData<OrderModel?>(null)


    private fun getOrderDetail(order: OrderModel) {
        /*viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { ordersRepository.getOrderDetails(order) },
                onSuccess = { orderDetail ->
                    _orderDetail.postValue(orderDetail)
                },
                isLoading = _isLoading
            )
        }*/
    }

    /**
     * Возвращает или устанавливает причину отмены заказа.
     */
    val orderCancelOwnReason = MutableLiveData("")

    /**
     * Возвращает модель выбора причины отмены заказа.
     */
    val orderCancelModel = OrderCancelModel(
        _items = listOf(
            OrderCancelModel.Item(
                titleRes = R.string.order_details_cancel_sheet_error_in_order,
            ),
            OrderCancelModel.Item(
                titleRes = R.string.order_details_cancel_sheet_not_buy,
            ),
            OrderCancelModel.Item(
                titleRes = R.string.order_details_cancel_sheet_another_pharmacy,
            ),
            OrderCancelModel.Item(
                titleRes = R.string.order_details_cancel_sheet_long_time,
            ),
            OrderCancelModel.Item(
                titleRes = R.string.order_details_cancel_sheet_already_bought,
            ),
            OrderCancelModel.Item(
                titleRes = R.string.order_details_cancel_sheet_other,
            ),
        ),
    ) {

    }

    /**
     * Отменить заказ.
     */
    fun cancelOrder(success: () -> Unit) {
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