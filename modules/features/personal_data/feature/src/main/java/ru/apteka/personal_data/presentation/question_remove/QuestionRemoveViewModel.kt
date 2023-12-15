package ru.apteka.personal_data.presentation.question_remove


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.DownTimer
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Представляет модель представления "Вопрос удаления аккаунта".
 */
@HiltViewModel
class QuestionRemoveViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    private val accountsPreferences: AccountsPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    private val downTimer = DownTimer(60)
    private val _leftTime = MutableLiveData<String?>(null)

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
     * Устанавливает или возвращает код.
     */
    val code = MutableLiveData("")

    /**
     * Возвращает код без маски.
     */
    /*val codeRaw = code.map {
        _confirmCodeStatus.postValue(null)
        it.replace(" ", "")
    }*/


    private val _pinCodeVisible = MutableLiveData(false)

    /**
     * Возвращает флаг видимости поля для ввода пинкода.
     */
    val isPinCodeVisible: LiveData<Boolean> = _pinCodeVisible


    /**
     * Возвращает флаг доступности кнопки удаления.
     */
    val removeBtnEnabled = MediatorLiveData<Boolean>().apply {
        fun checkEnabled() {
            postValue(
                (rb1.value!! || rb2.value!! || rb3.value!! || rb4.value!! || (rb5.value!! && ownReason.value!!.isNotEmpty())) && !isPinCodeVisible.value!! && !isLoading.value!!
            )
        }

        addSource(rb1) { checkEnabled() }
        addSource(rb2) { checkEnabled() }
        addSource(rb3) { checkEnabled() }
        addSource(rb4) { checkEnabled() }
        addSource(rb5) { checkEnabled() }
        addSource(ownReason) { checkEnabled() }
        addSource(isPinCodeVisible) { checkEnabled() }
    }


    /**
     * Осталось времени до повторения отправки кода.
     */
    val leftTime: LiveData<String?> = _leftTime

    //private val _confirmCodeStatus = MutableLiveData<ConfirmCodeStatus?>(null)

    /**
     * Возвращает статус подтверждения кода.
     */
    //val confirmCodeStatus: LiveData<ConfirmCodeStatus?> = _confirmCodeStatus

    /**
     * Получает код повторно.
     */
    val onNewCode: (View) -> Unit = {
        _pinCodeVisible.value = true
        if (leftTime.value == null) {
            viewModelScope.launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { /*loginRepository.getNewCode("kkhghk")*/ },
                    isLoading = _isLoading
                )
            }
            startDownTime()
        }
    }

    /**
     * Проверяет код.
     */
    /*fun checkCode(success: () -> Unit) {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { loginRepository.checkCode(codeRaw.value!!) },
                onSuccess = { sendCodeResult ->
                    mainThread {
                        if (sendCodeResult.success) {
                            _confirmCodeStatus.value = ConfirmCodeStatus.SUCCESS
                            accountsPreferences.account = null
                            success()
                        } else {
                            _confirmCodeStatus.value = ConfirmCodeStatus.ERROR
                        }
                    }
                },
                isLoading = _isLoading
            )
        }
    }*/

    private fun startDownTime() {
        viewModelScope.launchIO {
            downTimer.startWithFlow().map {
                it?.let {
                    val minutes = it / 60
                    val seconds = it % 60
                    val format = DecimalFormat("00")
                    "${format.format(minutes)}:${format.format(seconds)}"
                }
            }.collect {
                _leftTime.postValue(it)
            }
        }
    }

}