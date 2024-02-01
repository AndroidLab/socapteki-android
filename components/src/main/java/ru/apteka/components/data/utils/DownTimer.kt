package ru.apteka.components.data.utils

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val MILLISECOND = 1000L

/**
 * Представляет таймер обратного отсчета.
 * @param timeInSecond Времяя в секундах.
 */
class DownTimer(
    timeInSecond: Int,
    private val onTimeTick: (Int) -> Unit = {},
    private val onTimeFinish: () -> Unit = {}
) : CountDownTimer(timeInSecond * MILLISECOND, MILLISECOND) {

    private val _lefTimeFlow = MutableStateFlow<Int?>(null)

    override fun onTick(p0: Long) {
        val leftTime = (p0 / MILLISECOND).toInt()
        onTimeTick(leftTime)
        _lefTimeFlow.value = leftTime
    }

    override fun onFinish() {
        onTimeFinish()
        _lefTimeFlow.value = null
    }

    /**
     * Запускает таймер.
     */
    fun startTimer() {
        if (_lefTimeFlow.value == null) {
            start()
        }
    }

    /**
     * Запускает таймер.
     */
    fun startWithFlow() = _lefTimeFlow.asStateFlow().also {
        start()
    }

    /**
     * Отменяет таймер.
     */
    fun cancelTimer() {
        _lefTimeFlow.value = null
        cancel()
    }
}
