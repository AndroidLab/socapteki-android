package ru.apteka.components.data.services.message_notice_service

import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import javax.inject.Singleton

/**
 * Описывает свойства и методы вызова сообщений.
 */
@Singleton
interface IMessageNoticeService {

    /**
     * Показывает диалог.
     */
    fun showCommonDialog(dialogModel: DialogModel)

    /**
     * Показывает toast.
     */
    fun showCommonToast(message: MessageModel)
}