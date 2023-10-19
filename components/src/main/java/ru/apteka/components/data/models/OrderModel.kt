package ru.apteka.components.data.models

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.apteka.components.data.utils.Calendar
import ru.apteka.components.data.utils.formatDate


/**
 * Представляет модель заказа.
 */
@Parcelize
data class OrderModel(
    val number: Int,
    val status: OrderStatus,
    val date: Int
): Parcelable {
    /**
     * Возвращает форматированную дату.
     */
    fun getFormatDate(context: Context) = Calendar(date).formatDate(context)
}
