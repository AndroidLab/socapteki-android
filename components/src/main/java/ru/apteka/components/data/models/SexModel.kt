package ru.apteka.components.data.models


/**
 * Представляет модель для выбора пола.
 */
class SexModel(
    _items: List<Item>,
    _onItemSelected: (Item?) -> Unit
): SelectableModel<SexModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        val sex: Int,
    ): SelectableItem()
}