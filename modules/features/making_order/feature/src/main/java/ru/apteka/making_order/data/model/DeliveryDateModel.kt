package ru.apteka.making_order.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Представляет модель даты и времени доставки.
 */
@Parcelize
data class DeliveryDateModel(
    val date: Long,
    val time: DeliveryTimeModel.Item
): Parcelable
