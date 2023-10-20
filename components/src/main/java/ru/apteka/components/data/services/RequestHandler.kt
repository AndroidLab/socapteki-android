package ru.apteka.components.data.services

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.R
import ru.apteka.components.data.services.error_notice_service.IErrorNoticeService
import ru.apteka.components.data.services.error_notice_service.models.IRequestError
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.net.ssl.SSLException

/**
 * Представляет методы для обработки запросов.
 * @param errorNoticeService Сервис уведомлений об ошибке.
 */
class RequestHandler @Inject constructor(
    private val errorNoticeService: IErrorNoticeService
) {

    /**
     * Выполняет запрос [onRequest] с обработкой ответа и ошибок по умолчанию.
     * @param onRequest Запрос.
     * @param onSuccess Callback с результатом выполнения запроса.
     * @param onFailure Callback возвращающий ошибку.
     * @param isLoading Callback изменения флага, отвечающего за состояние загрузки.
     */
    suspend fun <T> handleApiRequest(
        onRequest: suspend () -> T,
        onSuccess: suspend (result: T) -> Unit = {},
        onFailure: (error: Throwable) -> Unit = {
            errorNoticeService.showError(
                IRequestError.RequestErrorStringMsg(
                    it.message.toString()
                )
            )
        },
        isLoading: MutableLiveData<Boolean>
    ) = handleApiRequest(onRequest, onSuccess, onFailure) {
        isLoading.postValue(it)
    }


    /**
     * Выполняет запрос [onRequest] с обработкой ответа и ошибок по умолчанию.
     * @param onRequest Запрос.
     * @param onSuccess Callback с результатом выполнения запроса.
     * @param onFailure Callback возвращающий ошибку.
     * @param onLoading Callback изменения флага, отвечающего за состояние загрузки.
     */
    suspend fun <T> handleApiRequest(
        onRequest: suspend () -> T,
        onSuccess: suspend (result: T) -> Unit = {},
        onFailure: (error: Throwable) -> Unit = {
            errorNoticeService.showError(
                IRequestError.RequestErrorStringMsg(
                    it.message.toString()
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
        } catch (e: CancellationException) {
            throw e
            // Возникают при отмене работы, выполняемой в Coroutine.
            // Например, может возникнуть, если VM инициировала запрос и была закрыта до его завершения.
            // Нет необходимости как-либо реагировать на отмену.
        } catch (e: Throwable) {
            throwableHandler(e) {
                onFailure(it)
            }
            Result.failure(e)
        } finally {
            onLoading(false)
        }
    }

    private fun throwableHandler(
        e: Throwable,
        unhandledThrowable: (e: Throwable) -> Unit
    ) {
        when (e) {
            is SocketTimeoutException, is ConnectException, is UnknownHostException, is SSLException -> {
                IRequestError.RequestErrorResMsg(
                    R.string.http_error_not_connection
                )
            }

            else -> {
                unhandledThrowable(e)
            }
        }
    }

}