package ru.apteka.components.data.services.message_notice_service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.utils.launchIO
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Сервис уведомлений об ошибках.
 */
@Singleton
class MessageNoticeService @Inject constructor() : IMessageNoticeService {
    private val _commonDialog =
        MutableSharedFlow<DialogModel>()

    /**
     * Возвращает модель для показа диалога.
     */
    val commonDialog: SharedFlow<DialogModel> = _commonDialog.asSharedFlow()

    override fun showCommonDialog(
        dialogModel: DialogModel
    ) {
        GlobalScope.launchIO {
            _commonDialog.emit(dialogModel)
        }
    }


    private val _commonToast =
        MutableSharedFlow<MessageModel>()

    /**
     * Возвращает модель для показа toast.
     */
    val commonToast: SharedFlow<MessageModel> = _commonToast.asSharedFlow()

    override fun showCommonToast(message: MessageModel) {
        GlobalScope.launchIO {
            _commonToast.emit(message)
        }
    }
}