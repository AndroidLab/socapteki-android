package ru.apteka.components.data.services.error_notice_service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.services.error_notice_service.models.IRequestError
import ru.apteka.components.data.utils.launchIO
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class ErrorNoticeService @Inject constructor() : IErrorNoticeService {
    private val _error =
        MutableSharedFlow<IRequestError>()

    /**
     * Возвращает ошибку запроса.
     */
    val error: SharedFlow<IRequestError> = _error.asSharedFlow()

    override fun showError(
        errorRequest: IRequestError
    ) {
        GlobalScope.launchIO {
            _error.emit(errorRequest)
        }
    }

}