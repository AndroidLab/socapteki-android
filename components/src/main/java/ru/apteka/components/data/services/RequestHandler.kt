package ru.apteka.components.data.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.net.ssl.SSLException


/**
 * Представляет методы для обработки запросов.
 * @param messageNoticeService Сервис уведомлений.
 */
class RequestHandler @Inject constructor(
    private val messageNoticeService: IMessageNoticeService
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
            messageNoticeService.showCommonToast(
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
            messageNoticeService.showCommonToast(
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
            //Log.d("myL", "requestResult " + requestResult)
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
            Log.d("myL", "6 " + e)
            onError(e)
            Result.failure(e)
        } finally {
            onLoading(false)
        }
    }

}