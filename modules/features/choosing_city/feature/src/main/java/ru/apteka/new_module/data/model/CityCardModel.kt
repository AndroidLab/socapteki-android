package ru.apteka.new_module.data.model

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.user.models.CityModel


/**
 * Представляет модель карточки города.
 */
data class CityCardModel(
    val city: CityModel,
    val onItemClick: (CityCardModel) -> Unit,
    val isSelected: MutableLiveData<Boolean> = MutableLiveData(false)
)
