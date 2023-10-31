package ru.apteka.components.data.services.message_notice_service.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


/**
 * Представляет сообщение для уведомлений.
 * @param message Сообщение.
 * @param isHtml Флаг является ли текст html.
 */
@Parcelize
data class MessageModel(
    val message: @RawValue Any = "",
    val isHtml: Boolean = false
) : Parcelable
