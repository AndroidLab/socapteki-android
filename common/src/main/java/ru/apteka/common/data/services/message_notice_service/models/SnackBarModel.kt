package ru.apteka.common.data.services.message_notice_service.models

import android.app.Activity
import com.google.android.material.snackbar.Snackbar


/**
 * Представляет модель [Snackbar].
 * @property activity Активность.
 * @property imageRes Ресурс иконки.
 * @property message Сообщение.
 * @property subMessage Дополнительное сообщение.
 * @property messageLevel Уровень сообщения.
 * @property duration Длительность показа.
 * @property actionBtnText Текст кнопки действия.
 * @property onAction Обработчик действия.
 */
data class SnackBarModel(
    val activity: Activity,
    val imageRes: Int = -1,
    val message: Any = "",
    val subMessage: Any = "",
    val messageLevel: MessageLevel = MessageLevel.USUAL,
    val duration: Int = Snackbar.LENGTH_LONG,
    val actionBtnText: Any = "",
    val onAction: () -> Unit = {}
)
