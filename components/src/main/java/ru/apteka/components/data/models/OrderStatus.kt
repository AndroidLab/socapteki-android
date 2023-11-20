package ru.apteka.components.data.models

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import ru.apteka.components.R


/**
 * Перечисляет статусы заказов.
 */
enum class OrderStatus(
    @StringRes val title: Int, @ColorRes val textColor: Int, @ColorRes val backgroundColor: Int
) {
    /**
     * Новый.
     */
    NEW(R.string.order_status_new, R.color.color_primary, R.color.color_surface_variant),

    /**
     * В обработке.
     */
    IN_PROCESSING(R.string.order_status_in_processing, R.color.white, R.color.orange),

    /**
     * Готов к получению.
     */
    READY_TO_RECEIVE(R.string.order_status_ready_to_receive, R.color.white, R.color.color_primary),

    /**
     * Передан курьеру.
     */
    TRANSFERRED_TO_COURIER(
        R.string.order_status_transferred_to_courier,
        R.color.white,
        R.color.order_status_transferred_to_courier_background
    ),

    /**
     * Доставка перенесена.
     */
    DELIVERY_POSTPONED(
        R.string.order_status_delivery_postponed,
        R.color.white,
        R.color.order_status_delivery_postponed_background
    ),

    /**
     * Бронирование продлено.
     */
    BOOKING_EXTENDED(
        R.string.order_status_booking_extended,
        R.color.white,
        R.color.order_status_booking_extended_background
    ),

    /**
     * Получен.
     */
    RECEIVED(R.string.order_status_received, R.color.white, R.color.light_black),

    /**
     * Отменен.
     */
    CANCELED(R.string.order_status_canceled, R.color.white, R.color.red),

    /**
     * Ожидает оплаты.
     */
    AWAITING_PAYMENT(R.string.order_awaiting_payment, R.color.red, R.color.pink);

    fun getTextColor(context: Context, orderStatus: OrderStatus) =
        ContextCompat.getColor(context, orderStatus.textColor)

    fun getBackgroundColor(context: Context, orderStatus: OrderStatus) =
        ContextCompat.getColor(context, orderStatus.backgroundColor)
}