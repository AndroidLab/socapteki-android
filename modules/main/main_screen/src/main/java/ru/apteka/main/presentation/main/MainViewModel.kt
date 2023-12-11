package ru.apteka.main.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IBottomSheetService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.main.data.BottomAppBarModel
import javax.inject.Inject


/**
 * Представляет модель представления "Основной экран".
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    val bottomSheetService: IBottomSheetService,
    val basketService: BasketService,
    val accountsPreferences: AccountsPreferences,
    val userPreferences: UserPreferences,
    val favoriteService: FavoriteService
): ViewModel() {

    /**
     *
     */
    val bottomAppBar = BottomAppBarModel()

    init {
        navigationManager.onSelectItemId = {
            bottomAppBar.onItemSelected(it)
        }
    }

}