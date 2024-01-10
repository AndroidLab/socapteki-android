package ru.apteka.orders.presentation.order_details

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
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
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.orders.R
import ru.apteka.product_card_api.R as ProductCardApiR
import ru.apteka.orders.data.models.OrderDetailProductCard
import ru.apteka.orders.data.models.OrderExtendBookingModel
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import javax.inject.Inject


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
    val order = MutableLiveData<OrderModel?>(null)

    /**
     * Возвращает продукцию в заказе.
     */
    val orderProducts = order.map {
        it?.products?.map { product ->
            OrderDetailProductCard(
                product = product,
                onCardClick = {
                    navigationManager.generalNavController.navigateWithAnim(
                        ProductCardApiR.id.product_card_graph, bundleOf(
                            PRODUCT_CARD_ARGUMENT_PRODUCT to it
                        )
                    )
                }
            )
        } ?: emptyList()
    }

    /**
     * Возвращает или устанавливает текст комментария по сделанному заказу.
     */
    val orderOpinion = MutableLiveData("")

    /**
     * Возвращает модель выбора срока продления бронирования.
     */
    val orderExtendBookingModel = OrderExtendBookingModel(
        _items = listOf(
            OrderExtendBookingModel.Item(
                titleRes = R.string.order_details_extend_booking_1_day,
            ),
            OrderExtendBookingModel.Item(
                titleRes = R.string.order_details_extend_booking_3_day,
            ),
            OrderExtendBookingModel.Item(
                titleRes = R.string.order_details_extend_booking_5_day,
            ),
        ),
    ) {

    }

    /**
     * Отравляет мнение о заказе.
     */
    fun sendOpinion(success: () -> Unit) {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            mainThread {
                success()
            }
            _isLoading.postValue(false)
        }
    }

    /**
     * Продлить бронирование.
     */
    fun extendBooking() {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _isLoading.postValue(false)
        }
    }


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


}