package ru.apteka.pharmacies_map.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.common.data.RequestHandler
import ru.apteka.common.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки на карте".
 */
@HiltViewModel
class PharmaciesMapViewModel @Inject constructor(
    private val requestHandler: RequestHandler
) : BaseViewModel() {


    init {

    }

}