package ru.apteka.components.data.models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель выбора.
 */
abstract class SelectableModel<T: SelectableModel.SelectableItem>(
    val items: List<T>,
    val onItemSelected: (T) -> Unit
) {

    open class SelectableItem {
        val isItemSelected = MutableLiveData(false)
        fun onItemClick() {
            isItemSelected.value = !isItemSelected.value!!
        }
    }

    val mediator = MediatorLiveData(true).apply {
        items.forEach { item ->
            addSource(item.isItemSelected) {
                if (it == true) {
                    onItemSelected(item)
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
