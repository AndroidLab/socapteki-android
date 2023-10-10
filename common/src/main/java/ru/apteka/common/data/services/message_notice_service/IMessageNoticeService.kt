package ru.apteka.common.data.services.message_notice_service

import ru.apteka.common.data.services.message_notice_service.models.DialogModel
import javax.inject.Singleton

/**
 * Описывает свойства и методы вызова сообщений.
 */
@Singleton
interface IMessageNoticeService {

    /**
     * Показывает диалог.
     */
    fun showCommonDialog(dialogModel: DialogModel?)

}