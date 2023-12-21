package ru.apteka.profile.presentation.profile_management

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Управление профилем".
 */
@HiltViewModel
class ProfileManagementViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    val accountsPreferences: AccountsPreferences,
    messageNoticeService: IMessageNoticeService,
    navigationManager: NavigationManager
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    init {

    }

}