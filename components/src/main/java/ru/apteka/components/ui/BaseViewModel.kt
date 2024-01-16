package ru.apteka.components.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager

/**
 * Представляет базовую ViewModel.
 */
abstract class BaseViewModel(
    val navigationManager: NavigationManager,
    val messageService: IMessageService,
) : ViewModel() {
    /**
     * Флаг, возвращает значение, происходит ли загрузка данных.
     */
    protected val _isLoading = MutableLiveData(false)

    /**
     * Возвращает флаг, указывающий, что происходит загрузка данных.
     */
    val isLoading: LiveData<Boolean> = _isLoading


    /**
     * Возвращает обработчик ошибок.
     */
    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        context.cancel()
    }

    fun viewModelScopeLaunch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            block()
        }
    }

}