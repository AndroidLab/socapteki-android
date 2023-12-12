package ru.apteka.notifications.data.model

import androidx.annotation.StringRes
import ru.apteka.notifications.R


/**
 * Перечисляет типы фильтров заказов.
 */
enum class NotificationFilter(@StringRes val title: Int) {
    /**
     * Все.
     */
    ALL(R.string.notifications_filter_all),

    /**
     * Заказы.
     */
    ORDERS(R.string.notifications_filter_orders),

    /**
     * Акции.
     */
    STOCKS(R.string.notifications_filter_stocks),

}