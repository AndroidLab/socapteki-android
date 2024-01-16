package ru.apteka.components.data.services

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.R
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.net.ssl.SSLException


/**
 * Представляет методы для обработки запросов.
 * @param messageService Сервис уведомлений.
 */
class RequestHandler @Inject constructor(
    private val messageService: IMessageService
) {

    /**
     * Выполняет запрос [onRequest] с обработкой ответа и ошибок по умолчанию.
     * @param onRequest Запрос.
     * @param onSuccess Callback с результатом выполнения запроса.
     * @param onFailure Callback возвращающий неудачу.
     * @param onError Callback возвращающий ошибку.
     * @param isLoading Callback изменения флага, отвечающего за состояние загрузки.
     */
    suspend fun <T> handleApiRequest(
        onRequest: suspend () -> T,
        onSuccess: suspend (result: T) -> Unit = {},
        onFailure: suspend (error: Throwable) -> Unit = {},
        onError: (error: Throwable) -> Unit = {
            messageService.showCommonToast(
                message = MessageModel(
                    message = it.message.toString()
                )
            )
        },
        isLoading: MutableLiveData<Boolean>
    ) = handleApiRequest(onRequest, onSuccess, onError) {
        isLoading.postValue(it)
    }


    /**
     * Выполняет запрос [onRequest] с обработкой ответа и ошибок по умолчанию.
     * @param onRequest Запрос.
     * @param onSuccess Callback с результатом выполнения запроса.
     * @param onFailure Callback возвращающий неудачу.
     * @param onError Callback возвращающий ошибку.
     * @param onLoading Callback изменения флага, отвечающего за состояние загрузки.
     */
    suspend fun <T> handleApiRequest(
        onRequest: suspend () -> T,
        onSuccess: suspend (result: T) -> Unit = {},
        onFailure: suspend (error: Throwable) -> Unit = {},
        onError: (error: Throwable) -> Unit = {
            messageService.showCommonToast(
                message = MessageModel(
                    message = it.message.toString()
                )
            )
        },
        onLoading: (loading: Boolean) -> Unit = {}
    ): Result<T> {
        onLoading(true)
        return try {
            val requestResult = onRequest.invoke()
            onSuccess(requestResult)
            Result.success(requestResult)
        } catch (e: Throwable) {
            when (e) {
                is CancellationException -> throw e
                is SocketTimeoutException, is ConnectException, is UnknownHostException, is SSLException -> {
                    showConnectionError()
                    throw e
                }
                else -> {
                    onError(e)
                    return Result.failure(e)
                }
            }
        } finally {
            onLoading(false)
        }
    }

    private fun showConnectionError() {
        messageService.showCommonToast(
            message = MessageModel(
                message = R.string.http_error_not_connection
            )
        )
    }

}