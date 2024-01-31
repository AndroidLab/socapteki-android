package ru.apteka.social

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.message_notice_service.BottomSheetService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Основного актвити".
 */
@HiltViewModel
class ActivityViewModel @Inject constructor(
    val bottomSheetService: BottomSheetService,
    val accountsPreferences: AccountsPreferences,
    val userPreferences: UserPreferences,
    val basketService: BasketService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {


}