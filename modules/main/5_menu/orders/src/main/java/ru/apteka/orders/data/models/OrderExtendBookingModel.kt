package ru.apteka.orders.data.models

import androidx.annotation.StringRes
import ru.apteka.components.data.models.SelectableModel
import java.util.UUID


/**
 * Представляет модель для выбора причины отмены заказа.
 */
class OrderExtendBookingModel(
    _items: List<Item>,
    _onItemSelected: (Item) -> Unit
): SelectableModel<OrderExtendBookingModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        @StringRes val titleRes: Int,
        val uuid: UUID = UUID.randomUUID()
    ): SelectableItem()
}