package ru.apteka.brands.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.utils.subListOrNull

abstract class LettersCardModel(
    val _title: String,
    val _items: List<LettersItemModel>,
) {
    private val _itemsLiveData = MutableLiveData(_items.subListOrNull(0, 4)!!)

    /**
     * Возвращает список пунктов.
     */
    val itemsLiveData: LiveData<List<LettersItemModel>> = _itemsLiveData

    /**
     * Показать все пункты.
     */
    fun showAll() {
        _itemsLiveData.value = _items
    }
}