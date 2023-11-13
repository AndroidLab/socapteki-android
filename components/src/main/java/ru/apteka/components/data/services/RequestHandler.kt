package ru.apteka.components.data.services

import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.error_notice_service.IErrorNoticeService
import ru.apteka.components.data.services.error_notice_service.models.IRequestError
import java.net.ConnectException
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
     * @param onFailure Callback возвращающий неудачу.
     * @param onError Callback возвращающий ошибку.
     * @param isLoading Callback изменения флага, отвечающего за состояние загрузки.
     */
    suspend fun <T> handleApiRequest(
        onRequest: suspend () -> T,
        onSuccess: suspend (result: T) -> Unit = {},
        onFailure: suspend (error: Throwable) -> Unit = {},
        onError: (error: Throwable) -> Unit = {
            errorNoticeService.showError(
                IRequestError.RequestErrorStringMsg(
                    it.message.toString()
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
        } catch (e: ConnectException) {
            throw e
        } catch (e: ConnectException) {
            throw e
        } catch (e: UnknownHostException) {
            throw e
        } catch (e: SSLException) {
            throw e
        } catch (e: Throwable) {
            onError(e)
            Result.failure(e)
        } finally {
            onLoading(false)
        }
    }

}