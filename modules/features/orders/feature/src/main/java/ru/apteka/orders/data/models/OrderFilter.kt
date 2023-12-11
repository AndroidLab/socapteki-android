package ru.apteka.orders.data.models

import androidx.annotation.StringRes
import ru.apteka.orders.R


/**
 * Перечисляет типы фильтров заказов.
 */
enum class OrderFilter(@StringRes val title: Int) {
    /**
     * Все.
     */
    ALL(R.string.orders_filter_all),

    /**
     * В работе.
     */
    IN_WORK(R.string.orders_filter_in_work),

    /**
     * Отмененные.
     */
    CANCELED(R.string.orders_filter_canceled),

    /**
     * Завершеные.
     */
    COMPLETED(R.string.orders_filter_completed)
}