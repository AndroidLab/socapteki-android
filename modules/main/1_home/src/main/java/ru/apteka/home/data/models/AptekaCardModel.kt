package ru.apteka.home.data.models

import androidx.lifecycle.MutableLiveData

data class AptekaCardModel(
    val apteka: AptekaModel,
    val onFavoriteClick: (AptekaCardModel) -> Unit,
    val isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
)
