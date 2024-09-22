package ru.apteka.work_with_us.data.model

import kotlinx.parcelize.RawValue
import ru.apteka.components.data.models.SelectableModel


/**
 *
 */
class JobOpeningsFilterModel(
    _items: @RawValue List<Item>,
    _onItemSelected: (Item?) -> Unit
) : SelectableModel<JobOpeningsFilterModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        val title: String,
    ) : SelectableItem()

    /**
     * Устанавливает выбранный пункт.
     */
    fun setItemSelected(position: Int) {
        items[position].isItemSelected.value = true
    }
}