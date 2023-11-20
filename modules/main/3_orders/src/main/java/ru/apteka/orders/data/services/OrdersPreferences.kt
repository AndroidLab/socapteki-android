package ru.apteka.orders.data.services


import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.apteka.components.data.utils.PreferencesDelegate
import ru.apteka.orders.data.models.OrderFilter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Представляет предпочтения выбранного города.
 * @param context Контекст приложения.
 */
@Singleton
class OrdersPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val ORDER_FILTER = "order_filter"
    }

    private val ordersPref =
        context.getSharedPreferences(OrdersPreferences::class.java.simpleName, Context.MODE_PRIVATE)


    private val _orderFilterFlow = MutableSharedFlow<OrderFilter>(replay = 1)

    /**
     * Возвращает поток выбранного фильтра заказа.
     */
    val orderFilterFlow: SharedFlow<OrderFilter> = _orderFilterFlow

    /**
     * Возвращет или устанавливает выбранный фильтры заказов.
     */
    var orderFilter: OrderFilter by PreferencesDelegate(
        ordersPref,
        ORDER_FILTER,
        OrderFilter.ALL, {
            it.name
        }, {
            OrderFilter.valueOf(it)
        },
        _orderFilterFlow
    )

}