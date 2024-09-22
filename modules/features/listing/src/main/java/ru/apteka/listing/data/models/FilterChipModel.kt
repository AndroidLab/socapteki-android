package ru.apteka.listing.data.models

import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель фильтра.
 */
data class FilterChipModel(
    val filter: IFilter,
    val isSelected: MutableLiveData<Boolean> = MutableLiveData(true),
    val onClick: (FilterChipModel) -> Unit = { isSelected.value = !isSelected.value!! },
    var onClickClose: (FilterChipModel) -> Unit = { },
)