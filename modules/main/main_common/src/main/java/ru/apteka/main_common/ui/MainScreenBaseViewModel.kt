package ru.apteka.main_common.ui

import androidx.lifecycle.asLiveData
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel


/**
 * Представляет модель представления "Базовый экран для главных экранов".
 */
abstract class MainScreenBaseViewModel(
    val accountsPreferences: AccountsPreferences,
    navigationManager: NavigationManager
) : BaseViewModel(navigationManager) {

    /**
     * Возвращает аккаунт.
     */
    val account = accountsPreferences.accountFlow.asLiveData()


}