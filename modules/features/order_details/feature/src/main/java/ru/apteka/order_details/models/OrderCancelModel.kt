package ru.apteka.order_details.models

import androidx.annotation.StringRes
import ru.apteka.components.data.models.SelectableModel
import java.util.UUID


/**
 * Представляет модель для выбора причины отмены заказа.
 */
class OrderCancelModel(
    _items: List<Item>,
    _onItemSelected: (Item?) -> Unit
): SelectableModel<OrderCancelModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        @StringRes val titleRes: Int,
        val uuid: UUID = UUID.randomUUID()
    ): SelectableItem()
}