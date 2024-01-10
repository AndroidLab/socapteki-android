package ru.apteka.social.presentation.auth.auth_confirm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.ConfirmationCodeModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.account.models.Account
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject
import kotlin.properties.Delegates


/**
 * Представляет модель представления "Подтверждение авторизации".
 */
@HiltViewModel
class AuthConfirmViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    private val accountsPreferences: AccountsPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    /**
     * Возвращает или устанавливает номер телефона.
     */
    var phoneNumber: String? by Delegates.observable(null) { d, old, new ->
        confirmationCode.requestCode()
    }

    /**
     * Возвращает событие навигации к главному экрану.
     */
    val isNavigationToMain = SingleLiveEvent<Unit>()

    /**
     * Возвращает модель подтверждения кода.
     */
    val confirmationCode = ConfirmationCodeModel(
        loginRepository = loginRepository,
        requestHandler = requestHandler,
        scope = viewModelScope,
        getPhoneRaw = {
            phoneNumber!!
        },
    )

    /**
     * Сохраняет персональные данные.
     */
    fun savePersonalData() {
        viewModelScope.launchIO {
            confirmationCode.confirmCode(
                request = { code ->
                    loginRepository.savePersonalDataPhone(
                        phoneNumber!!
                    )
                },
                success = {
                    accountsPreferences.account = Account(
                        phoneNumber = phoneNumber,
                        token = "12345"
                    )
                    isNavigationToMain.call()
                }
            )
        }
    }

}