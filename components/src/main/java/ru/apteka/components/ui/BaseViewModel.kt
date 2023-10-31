package ru.apteka.components.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import ru.apteka.components.R
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * Представляет базовую ViewModel.
 */
abstract class BaseViewModel(
    val navigationManager: NavigationManager,
    val messageNoticeService: IMessageNoticeService,
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
        Log.d("myL", "1")
        context.cancel()
        if (exception is SocketTimeoutException || exception is ConnectException || exception is UnknownHostException || exception is SSLException) {
            Log.d("myL", "2")
            handleConnectionError()
        }
    }

    private fun handleConnectionError() {
        messageNoticeService.showCommonToast(
            MessageModel(R.string.http_error_not_connection)
        )
        //viewModelScope = createCoroutineScope()
    }

    /**
     * Возвращает или устанавливает скоуп сервиса.
     */
    //var viewModelScope = createCoroutineScope()

    fun viewModelScopeLaunch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            block()
        }
    }


}