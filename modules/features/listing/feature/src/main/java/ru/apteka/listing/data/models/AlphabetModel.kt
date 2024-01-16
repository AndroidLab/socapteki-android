package ru.apteka.listing.data.models

import ru.apteka.components.data.models.SelectableModel


/**
 * Представляет модель для поиска по алфовиту.
 */
class AlphabetModel(
    _items: List<Item>,
    _onItemSelected: (Item) -> Unit
): SelectableModel<AlphabetModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        val name: String,
    ): SelectableItem()
}