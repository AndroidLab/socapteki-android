package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Перечисляет статусы заказов.
 */
enum class OrderStatus {
    /**
     * В работе.
     */
    IN_WORK,

    /**
     * Отмененный.
     */
    CANCELED,

    /**
     * Завершенный.
     */
    COMPLETED
}