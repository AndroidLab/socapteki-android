package ru.apteka.components.data.services.message_notice_service

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class MessageNoticeService @Inject constructor() : IMessageNoticeService {
    private val _commonDialog =
        MutableSharedFlow<DialogModel?>(replay = 1)

    /**
     * Возвращает модель для показа диалога.
     */
    val commonDialog: SharedFlow<DialogModel?> = _commonDialog.asSharedFlow()

    /**
     * Сбрасывает значение.
     */
    fun reset() {
        _commonDialog.tryEmit(null)
    }

    override fun showCommonDialog(
        dialogModel: DialogModel?
    ) {
        _commonDialog.tryEmit(dialogModel)
    }

}