package ru.apteka.components.data.models

import androidx.annotation.StringRes
import ru.apteka.components.R


/**
 * Перечисляет статусы заказов.
 */
enum class OrderStatus(@StringRes val title: Int) {
    /**
     * Все.
     */
    ALL(R.string.orders_all),

    /**
     * В работе.
     */
    IN_WORK(R.string.orders_in_work),

    /**
     * Отмененный.
     */
    CANCELED(R.string.orders_canceled),

    /**
     * Завершенный.
     */
    COMPLETED(R.string.orders_completed)
}