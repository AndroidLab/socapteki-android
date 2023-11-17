package ru.apteka.orders.data

import kotlinx.parcelize.RawValue
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.SelectableModel


/**
 *
 */
class OrderFilterModel(
    _items: @RawValue List<Item>,
    _onItemSelected: (Item) -> Unit
) : SelectableModel<OrderFilterModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        val status: OrderStatus,
    ) : SelectableItem()

    /**
     * Устанавливает выбранный пункт.
     */
    fun setItemSelected(position: Int) {
        items[position].isItemSelected.value = true
    }
}