package ru.apteka.components.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * Представляет [LiveData] ограниченную одластью [ViewModel] для отправки данных.
 */
context(E)
class ScopedLiveData<E, T>(
    value: T? = null
): LiveData<T>(value) {

    /**
     * Отправляет значение в [LiveData].
     */
    context(E)
    fun postValue(value: Any?) {
        super.postValue(value as T)
    }

    /**
     * Устанавливает значение в [LiveData].
     */
    context(E)
    fun setValue(value: Any?) {
        super.setValue(value as T)
    }
}