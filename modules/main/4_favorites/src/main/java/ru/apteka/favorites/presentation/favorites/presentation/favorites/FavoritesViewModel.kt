package ru.apteka.favorites.presentation.favorites.presentation.favorites

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.main_common.ui.MainScreenBaseViewModel
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
    accountsPreferences: AccountsPreferences
) : MainScreenBaseViewModel(
    accountsPreferences,
    navigationManager
) {

    init {

    }

}