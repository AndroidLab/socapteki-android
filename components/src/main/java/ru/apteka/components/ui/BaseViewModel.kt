package ru.apteka.components.ui

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
import ru.apteka.components.data.utils.ScopedLiveData

/**
 * Представляет базовую ViewModel.
 */
abstract class BaseViewModel(
    val navigationManager: NavigationManager,
    val messageService: IMessageService,
) : ViewModel() {
    /**
     * Возвращает флаг, указывающий, что происходит загрузка данных.
     */
    val isLoading = ScopedLiveData(false)

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