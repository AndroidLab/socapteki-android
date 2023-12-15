package ru.apteka.making_order.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


/**
 * Представляет модель получателя.
 */
@Parcelize
data class RecipientModel(
    val fio: String?,
    val phone: String,
): Parcelable {
    @IgnoredOnParcel
    var onRemove: (() -> Unit)? = null
}
