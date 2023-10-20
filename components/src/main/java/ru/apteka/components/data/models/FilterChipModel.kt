package ru.apteka.components.data.models

import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель фильтра.
 */
data class FilterChipModel(
    val text: String,
    val isSelected: MutableLiveData<Boolean> = MutableLiveData(true),
    val onClick: (FilterChipModel) -> Unit = { isSelected.value = !isSelected.value!! },
    var onClickClose: (FilterChipModel) -> Unit = { },
)