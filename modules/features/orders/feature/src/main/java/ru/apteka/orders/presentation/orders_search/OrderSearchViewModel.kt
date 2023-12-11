package ru.apteka.orders.presentation.orders_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.repository.orders.OrdersRepository
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.utils.debounce
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Поиск заказов".
 */
@HiltViewModel
class OrderSearchViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val ordersRepository: OrdersRepository,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    private val _foundOrders = MutableLiveData<List<OrderModel>>(emptyList())

    /**
     * Возвращает найденные заказы.
     */
    val foundOrders: LiveData<List<OrderModel>> = _foundOrders

    /**
     * Возвращает или устанавливает номер заказа для поиска.
     */
    var orderNumberQuery = ""

    /**
     * Возвращает текст поиска заказа.
     */
    val onOrderSearchTextChange = viewModelScope.debounce<String>(1500L) { value ->
        if (value.isNotBlank()) {
            if (value != orderNumberQuery) {
                orderNumberQuery = value
                ordersSearch(value.toInt())
            }
        } else {
            clearData()
        }
    }


    private suspend fun ordersSearch(value: Int) {
        requestHandler.handleApiRequest(
            onRequest = {
                ordersRepository.findOrdersByNumber(value)
            },
            onSuccess = { orders ->
                _foundOrders.postValue(orders)
            },
            isLoading = _isLoading
        )
    }

    /**
     * Очищает данные.
     */
    private fun clearData() {
        _foundOrders.postValue(emptyList())
    }


}