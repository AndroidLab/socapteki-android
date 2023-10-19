package ru.apteka.components.data.utils

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * Представляет таймер обратного отсчета.
 * @param timeInSecond Времяя в секундах.
 */
class DownTimer(timeInSecond: Int) : CountDownTimer(timeInSecond * 1000L, 1000) {

    private val _lefTimeFlow = MutableStateFlow<Int?>(null)

    override fun onTick(p0: Long) {
        _lefTimeFlow.value = (p0 / 1000).toInt()
    }

    override fun onFinish() {
        _lefTimeFlow.value = null
    }

    /**
     * Запускает таймер.
     */
    fun startWithFlow() = _lefTimeFlow.asStateFlow().also {
        start()
    }
}