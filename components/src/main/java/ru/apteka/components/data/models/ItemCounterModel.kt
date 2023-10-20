package ru.apteka.components.data.models

import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель счетчика.
 */
data class ItemCounterModel(
    val onMinus: () -> Unit,
    val onPlus: () -> Unit,
    val value: MutableLiveData<Int> = MutableLiveData(0)
)
