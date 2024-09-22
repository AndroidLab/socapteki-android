package ru.apteka.orders.data.models

import kotlinx.parcelize.RawValue
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.SelectableModel


/**
 * Представляет модель фильтра заказов.
 */
class OrderFilterModel(
    _items: @RawValue List<Item>,
    _onItemSelected: (Item?) -> Unit
) : SelectableModel<OrderFilterModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {

    data class Item(
        val status: OrderStatus?,
    ) : SelectableItem()

}