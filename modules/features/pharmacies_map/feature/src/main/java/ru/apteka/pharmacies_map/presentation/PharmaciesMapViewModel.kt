package ru.apteka.pharmacies_map.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки на карте".
 */
@HiltViewModel
class PharmaciesMapViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    val navigationManager: NavigationManager
) : BaseViewModel() {


    init {

    }

}