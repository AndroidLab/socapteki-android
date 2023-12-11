package ru.apteka.profile.data.models

import androidx.lifecycle.MutableLiveData

data class AptekaCardModel(
    val apteka: ru.apteka.profile.data.models.PharmacyModel,
    val onFavoriteClick: (AptekaCardModel) -> Unit,
    val isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
)
