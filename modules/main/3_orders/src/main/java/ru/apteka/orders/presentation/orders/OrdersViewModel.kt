package ru.apteka.orders.presentation.orders

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.components.data.models.FilterChipModel
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.repository.orders.OrdersRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import ru.apteka.orders.R
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
    accountsPreferences: AccountsPreferences,
    @ApplicationContext context: Context
) : MainScreenBaseViewModel(
    accountsPreferences,
    navigationManager
) {

    /**
     * Возвращает модель фильтра 'Отмененные'.
     */
    val filterCanceled = FilterChipModel(context.getString(R.string.orders_canceled_filter)).apply {
        userPreferences.disabledOrderFilters.contains(OrderStatus.CANCELED.name).let { value ->
            if (value) isSelected.postValue(false)
        }
    }

    /**
     * Возвращает модель фильтра 'В работе'.
     */
    val filterInWork = FilterChipModel(context.getString(R.string.orders_in_work_filter)).apply {
        userPreferences.disabledOrderFilters.contains(OrderStatus.IN_WORK.name).let { value ->
            if (value) isSelected.postValue(false)
        }
    }

    /**
     * Возвращает модель фильтра 'Завершенные'.
     */
    val filterCompleted = FilterChipModel(context.getString(R.string.orders_completed_filter)).apply {
        userPreferences.disabledOrderFilters.contains(OrderStatus.COMPLETED.name).let { value ->
            if (value) isSelected.postValue(false)
        }
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
                buildList {
                    addAll(
                        _orders.value!!.filter { filterCanceled.isSelected.value == true && it.status == OrderStatus.CANCELED }
                    )
                    addAll(
                        _orders.value!!.filter { filterInWork.isSelected.value == true && it.status == OrderStatus.IN_WORK }
                    )
                    addAll(
                        _orders.value!!.filter { filterCompleted.isSelected.value == true && it.status == OrderStatus.COMPLETED }
                    )
                }.sortedByDescending { it.date }
            )
            userPreferences.disabledOrderFilters = buildSet {
                if (filterCanceled.isSelected.value == false) add(OrderStatus.CANCELED.name)
                if (filterInWork.isSelected.value == false) add(OrderStatus.IN_WORK.name)
                if (filterCompleted.isSelected.value == false) add(OrderStatus.COMPLETED.name)
            }
        }

        addSource(_orders) {
            filterOrders()
        }

        addSource(filterCanceled.isSelected) {
            filterOrders()
        }

        addSource(filterInWork.isSelected) {
            filterOrders()
        }

        addSource(filterCompleted.isSelected) {
            filterOrders()
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