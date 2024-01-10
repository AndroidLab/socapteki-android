package ru.apteka.basket.data.models

import ru.apteka.components.data.models.SelectableModel


/**
 * Представляет модель фильтра для 'Всегда пригодится'.
 */
class AlwaysUsefulFilterModel(
    _items: List<Item>,
    _onItemSelected: (Item) -> Unit = {}
) : SelectableModel<AlwaysUsefulFilterModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {

    data class Item(
        val status: String,
    ) : SelectableItem()
}