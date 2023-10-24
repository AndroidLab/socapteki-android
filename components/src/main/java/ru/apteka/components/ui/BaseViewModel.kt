package ru.apteka.components.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.apteka.components.data.services.navigation_manager.NavigationManager

/**
 * Представляет базовую ViewModel.
 */
abstract class BaseViewModel(
    val navigationManager: NavigationManager
) : ViewModel() {
    /**
     * Флаг, возвращает значение, происходит ли загрузка данных.
     */
    protected val _isLoading = MutableLiveData(false)

    /**
     * Возвращает флаг, указывающий, что происходит загрузка данных.
     */
    val isLoading: LiveData<Boolean> = _isLoading

}