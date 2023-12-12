package ru.apteka.notifications.data.model

import kotlinx.parcelize.RawValue
import ru.apteka.components.data.models.SelectableModel


/**
 *
 */
class NotificationFilterModel(
    _items: @RawValue List<Item>,
    _onItemSelected: (Item) -> Unit
) : SelectableModel<NotificationFilterModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {

    data class Item(
        val status: NotificationFilter,
        val count: Int
    ) : SelectableItem()

    /**
     * Устанавливает выбранный пункт.
     */
    fun setItemSelected(position: Int) {
        items[position].isItemSelected.value = true
    }
}