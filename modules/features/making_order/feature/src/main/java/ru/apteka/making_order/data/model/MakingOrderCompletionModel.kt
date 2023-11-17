package ru.apteka.making_order.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Представляет модель данных по завершению заказа.
 */
@Parcelize
data class CompletionDataModel(
    val deliveryDate: DeliveryDateModel,
    val recipients: List<RecipientModel>
): Parcelable