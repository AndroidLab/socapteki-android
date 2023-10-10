package ru.apteka.social.presentation.auth.auth_confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.apteka.common.data.RequestHandler
import ru.apteka.common.data.utils.DownTimer
import ru.apteka.common.data.utils.launchIO
import ru.apteka.common.data.utils.mainThread
import ru.apteka.common.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.common.ui.BaseViewModel
import ru.apteka.social.domain.login.usecase.CheckCodeUseCase
import ru.apteka.social.domain.login.usecase.NewCodeUseCase
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.properties.Delegates


/**
 * Представляет модель представления "Подтверждение авторизации".
 */
@HiltViewModel
class AuthConfirmViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val newCodeUseCase: NewCodeUseCase,
    private val checkCodeUseCase: CheckCodeUseCase
) : BaseViewModel() {

    private val downTimer = DownTimer(60)
    private val _leftTime = MutableLiveData<String?>(null)

    /**
     * Возвращает или устанавливает номер телефона.
     */
    var phoneNumber: String by Delegates.notNull()

    /**
     * Осталось времени до повторения отправки кода.
     */
    val leftTime: LiveData<String?> = _leftTime

    /**
     * Устанавливает или возвращает код.
     */
    val code = MutableLiveData("")

    /**
     * Возвращает код без маски.
     */
    val codeRaw = code.map {   //TODO сделать от маски.
        _confirmCodeStatus.postValue(null)
        it.replace(" ", "")
    }

    /**
     * Возвращает событие навигации к главному экрану.
     */
    val isNavigationToMain = SingleLiveEvent<Unit>()


    private val _confirmCodeStatus = MutableLiveData<ConfirmCodeStatus?>(null)

    /**
     * Возвращает флаг успешного подтверждения кода.
     */
    val confirmCodeStatus: LiveData<ConfirmCodeStatus?> = _confirmCodeStatus


    /**
     * Получает код повторно.
     */
    fun getNewCode() {
        if (leftTime.value == null) {
            viewModelScope.launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { newCodeUseCase.execute(phoneNumber) },
                    isLoading = _isLoading
                )
            }
            startDownTime()
        }
    }

    /**
     * Проверяет код.
     */
    fun checkCodeNumber() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { checkCodeUseCase.execute(codeRaw.value!!) },
                onSuccess = { sendCodeResult ->
                    mainThread {
                        if (sendCodeResult.success) {
                            _confirmCodeStatus.value = ConfirmCodeStatus.SUCCESS
                            isNavigationToMain.call()
                        } else {
                            _confirmCodeStatus.value = ConfirmCodeStatus.ERROR
                        }
                    }
                },
                isLoading = _isLoading
            )
        }
    }

    init {
        startDownTime()
    }

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

    enum class ConfirmCodeStatus() {
        SUCCESS,
        ERROR
    }

}