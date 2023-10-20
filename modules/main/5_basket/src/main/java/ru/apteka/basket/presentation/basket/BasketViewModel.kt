package ru.apteka.basket.presentation.basket

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Избранное".
 */
@HiltViewModel
class BasketViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val basketService: BasketService,
    val navigationManager: NavigationManager
) : BaseViewModel() {


    init {

    }

}