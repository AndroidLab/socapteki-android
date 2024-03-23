package ru.apteka.components.data.models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.utils.ScopedLiveData


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

    /**
     * Возвращает или устанавливает выбранный элемент.
     */
    val selectedItem = ScopedLiveData<SelectableModel<T>, T?>(null)

    val mediator = MediatorLiveData(true).apply {
        items.forEach { item ->
            addSource(item.isItemSelected) {
                if (it == true) {
                    onItemSelected(item)
                    selectedItem.setValue(item)
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
