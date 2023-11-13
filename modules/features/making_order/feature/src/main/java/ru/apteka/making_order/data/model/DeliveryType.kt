package ru.apteka.making_order.data.model

import androidx.annotation.StringRes
import ru.apteka.making_order.R


/**
 * Перечисляет типы доставки.
 */
enum class DeliveryType(@StringRes val title: Int) {
    PICKUP(0),
    YANDEX(R.string.making_order_shipping_methods_yandex_delivery),
    COURIER(R.string.making_order_shipping_methods_сourier_delivery)
}
