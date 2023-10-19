package ru.apteka.components.data.models

import android.content.Context
import ru.apteka.components.data.utils.Calendar
import ru.apteka.components.data.utils.formatDate


/**
 * Представляет модель деталей заказа.
 */
data class OrderDetailsModel(
    val number: Int,
    val status: OrderStatus,
    val date: Int,
    val methodDelivery: String,
    val address: String,
    val totalProducts: Int,
    val bonusCount: String,
    val paymentMethod: String,
    val totalCost: String,
    val products: List<ProductModel>
) {
    /**
     * Возвращает форматированную дату.
     */
    fun getFormatDate(context: Context) = Calendar(date).formatDate(context)
}
