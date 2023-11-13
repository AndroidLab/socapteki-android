package ru.apteka.home.data.models

import androidx.lifecycle.MutableLiveData

data class AptekaCardModel(
    val apteka: PharmacyModel,
    val onFavoriteClick: (AptekaCardModel) -> Unit,
    val isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
)
