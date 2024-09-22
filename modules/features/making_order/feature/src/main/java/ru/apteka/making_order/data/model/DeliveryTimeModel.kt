package ru.apteka.making_order.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ru.apteka.components.data.models.SelectableModel


/**
 * Представляет время доставки.
 */
@Parcelize
class DeliveryTimeModel <T: SelectableModel.SelectableItem> (
    private val _items: @RawValue List<T>,
    private val _onItemSelected: (T?) -> Unit
) : SelectableModel<T>(
    _items,
    _onItemSelected
), Parcelable {
    @Parcelize
    data class Item(
        val timeFrom: String,
        val timeTo: String
    ): SelectableItem(), Parcelable {
        val timeFormat
            get() = "с $timeFrom - $timeTo"
    }
}