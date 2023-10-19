package ru.apteka.components.data.models

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель фильтра.
 */
data class FilterModel(
    @StringRes val text: Int,
    val isSelected: MutableLiveData<Boolean> = MutableLiveData(true),
    val onClick: (FilterModel) -> Unit = { isSelected.value = !isSelected.value!!}
)