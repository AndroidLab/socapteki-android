package ru.apteka.home.data.models

import ru.apteka.components.data.models.SelectableModel


/**
 * Представляет модель фильтра для акций.
 */
class PromotionFilterModel(
    _items: List<Item>,
    _onItemSelected: (Item?) -> Unit = {}
) : SelectableModel<PromotionFilterModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {

    data class Item(
        val status: String,
    ) : SelectableItem()
}