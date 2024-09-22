package ru.apteka.making_order.data.model

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * Представляет модель получателя.
 */
@Parcelize
@Serializable
data class RecipientModel(
    val fio: String?,
    val phone: String,
): Parcelable {

    /**
     * Возвращает флаг выбора пункта.
     */
    @Transient
    @IgnoredOnParcel
    val isItemSelected = MutableLiveData(false)

    /**
     * Возвращает или устанавливает обработчик клика по получателю.
     */
    @Transient
    @IgnoredOnParcel
    var onClick: (() -> Unit)? = null

    /**
     * Возвращает или устанавливает обработчик уаления получателя.
     */
    @Transient
    @IgnoredOnParcel
    var onRemove: (() -> Unit)? = null
}
