package ru.apteka.components.data.models

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.utils.DownTimer
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.text.DecimalFormat


/**
 * Представляет код подтверждения.
 */
data class ConfirmationCodeModel(
    val loginRepository: LoginRepository,
    val requestHandler: RequestHandler,
    val scope: CoroutineScope,
    val getPhoneRaw: () -> String,
) {
    private val downTimer = DownTimer(60)

    /**
     * Возвращает или устанавливает тип кода для получения (СМС или звонок).
     */
    val codeType = MutableLiveData(CodeType.SMS)

    /**
     * Осталось времени до повторения отправки кода.
     */
    val leftTime = ScopedLiveData<ConfirmationCodeModel, String?>(null)

    /**
     * Устанавливает или возвращает код.
     */
    val code = MutableLiveData("")

    /**
     * Возвращает код без маски.
     */
    val codeRaw = code.map {
        //isCodeError.postValue(false)
        it.replace(" ", "")
    }

    /**
     * Возвращает флаг получения кода.
     */
    val isLoading = ScopedLiveData(false)

    /**
     * Возвращает флаг успешного запроса кода (Вызывается 1 раз, при первом успешном запросе).
     */
    val isCodeRequested = ScopedLiveData(false)

    /**
     * Возвращает флаг ошибки подтверждения кода.
     */
    val isCodeConfirmError = ScopedLiveData(false)

    /**
     * Возвращает кол-во запросов пин кода.
     */
    val requestCounts = ScopedLiveData(0)

    /**
     * Возвращает обработчмк получения нового кода.
     */
    fun requestCode() {
        if (leftTime.value == null) {
            scope.launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { loginRepository.requestCode(getPhoneRaw()) },
                    onSuccess = {
                        requestCounts.postValue(requestCounts.value!! + 1)
                        isCodeRequested.postValue(true)
                    },
                    onLoading = {
                        isLoading.postValue(it)
                    }
                )
                startDownTime()
            }
        }
    }

    suspend fun confirmCode(
        request: suspend (String) -> Boolean,
        success: () -> Unit
    ) {
        requestHandler.handleApiRequest(
            onRequest = {
                request(codeRaw.value!!)
            },
            onSuccess = {
                mainThread {
                    success()
                }
            },
            onLoading = {
                isLoading.postValue(it)
            }
        )
    }

    private suspend fun startDownTime() {
        downTimer.startWithFlow().map {
            it?.let {
                val minutes = it / 60
                val seconds = it % 60
                val format = DecimalFormat("00")
                "${format.format(minutes)}:${format.format(seconds)}"
            }
        }.collect {
            leftTime.postValue(it)
        }
    }

    companion object {
        /**
         * Устанавливает маску ввода кода.
         * @param textView [TextView] - Отображение.
         * @param codeMask [String] - Маска.
         */
        @JvmStatic
        @BindingAdapter("app:codeMask")
        fun TextView.setCodeMask(codeMask: String) {
            MaskFormatWatcher(
                MaskImpl.createTerminated(
                    UnderscoreDigitSlotsParser().parseSlots(codeMask)
                )
            ).installOn(this)
        }
    }

    enum class CodeType {
        SMS,
        PHONE
    }
}

