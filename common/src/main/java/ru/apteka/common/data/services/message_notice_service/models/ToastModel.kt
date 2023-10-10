package ru.apteka.common.data.services.message_notice_service.models

import android.content.Context
import android.widget.Toast


/**
 * Представляет модель [Toast].
 * @param context Контекст.
 * @param message Сообщение.
 * @param duration Длительность показа.
 */
data class ToastModel(
    val context: Context,
    val message: String,
    val duration: Int = Toast.LENGTH_LONG
)