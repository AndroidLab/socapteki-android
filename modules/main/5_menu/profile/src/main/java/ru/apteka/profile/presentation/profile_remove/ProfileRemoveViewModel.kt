package ru.apteka.profile.presentation.profile_remove


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ConfirmationCodeModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Вопрос удаления аккаунта".
 */
@HiltViewModel
class ProfileRemoveViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    val accountsPreferences: AccountsPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    /**
     * Возвращает модель подтверждения кода.
     */
    val confirmationCode = ConfirmationCodeModel(
        loginRepository = loginRepository,
        requestHandler = requestHandler,
        scope = viewModelScope,
        getPhoneRaw = {
            ""
        },
    )

    /**
     * Устанавливает или возвращает флаг rb1.
     */
    val rb1 = MutableLiveData(false)

    /**
     * Устанавливает или возвращает флаг rb2.
     */
    val rb2 = MutableLiveData(false)

    /**
     * Устанавливает или возвращает флаг rb3.
     */
    val rb3 = MutableLiveData(false)

    /**
     * Устанавливает или возвращает флаг rb4.
     */
    val rb4 = MutableLiveData(false)

    /**
     * Устанавливает или возвращает флаг rb5.
     */
    val rb5 = MutableLiveData(false)


    /**
     * Устанавливает или возвращает свою причину.
     */
    val ownReason = MutableLiveData("")


    /**
     * Возвращает флаг доступности кнопки удаления.
     */
    val removeBtnEnabled = MediatorLiveData<Boolean>().apply {
        fun checkEnabled() {
            postValue(
                confirmationCode.codeRaw.value!!.length == 4 && (rb1.value!! || rb2.value!! || rb3.value!! || rb4.value!! || (rb5.value!! && ownReason.value!!.isNotEmpty())) && !isLoading.value!!
            )
        }

        addSource(confirmationCode.codeRaw) {
            checkEnabled()
        }
        addSource(rb1) { checkEnabled() }
        addSource(rb2) { checkEnabled() }
        addSource(rb3) { checkEnabled() }
        addSource(rb4) { checkEnabled() }
        addSource(rb5) { checkEnabled() }
        addSource(ownReason) { checkEnabled() }
        addSource(isLoading) { checkEnabled() }
    }

    init {
        confirmationCode.requestCode()
    }

    /**
     * Удаляет профиль.
     */
    fun profileRemove(success: () -> Unit) {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            mainThread {
                success()
            }
            _isLoading.postValue(false)
        }
    }
}