package ru.apteka.common.data.services.message_notice_service

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.common.data.services.message_notice_service.models.DialogModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class MessageNoticeService @Inject constructor() : IMessageNoticeService {
    private val _commonDialog =
        MutableSharedFlow<DialogModel?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    /**
     * Возвращает модель для показа диалога.
     */
    val commonDialog: SharedFlow<DialogModel?> = _commonDialog.asSharedFlow()

    override fun showCommonDialog(
        dialogModel: DialogModel?
    ) {
        _commonDialog.tryEmit(dialogModel)
    }

}