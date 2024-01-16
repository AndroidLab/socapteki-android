package ru.apteka.favorites.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Избранное".
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val basketService: BasketService,
    val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    init {

    }

}