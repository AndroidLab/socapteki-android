package ru.apteka.brands.data.model

import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.subListOrNull

abstract class LettersCardModel(
    val _title: String,
    val _items: List<LettersItemModel>,
) {

    /**
     * Возвращает список пунктов.
     */
    val itemsLiveData = ScopedLiveData(_items.subListOrNull(0, 4)!!)

    /**
     * Показать все пункты.
     */
    fun showAll() {
        itemsLiveData.setValue(_items)
    }
}