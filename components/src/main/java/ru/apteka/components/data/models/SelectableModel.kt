package ru.apteka.components.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель выбора.
 */
abstract class SelectableModel<T: SelectableModel.SelectableItem>(
    val items: List<T>,
    val onItemSelected: (T) -> Unit
) {

    /**
     * Представляет элемент выбора.
     */
    open class SelectableItem {
        val isItemSelected = MutableLiveData(false)
        fun onItemClick() {
            isItemSelected.value = !isItemSelected.value!!
        }
    }

    private val _selectedItem = MutableLiveData<T?>(null)

    /**
     * Возвращает или устанавливает выбранный элемент.
     */
    val selectedItem: LiveData<T?> = _selectedItem

    val mediator = MediatorLiveData(true).apply {
        items.forEach { item ->
            addSource(item.isItemSelected) {
                if (it == true) {
                    onItemSelected(item)
                    _selectedItem.value = item
                    items.forEach {
                        if (it != item) {
                            it.isItemSelected.value = false
                        }
                    }
                }
            }
        }
    }
}
