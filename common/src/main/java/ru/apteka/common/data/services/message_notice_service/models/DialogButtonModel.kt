package ru.apteka.common.data.services.message_notice_service.models

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/**
 * Представляет кнопку диалога.
 * @param text Текст.
 * @param action Действие.
 */
@Parcelize
data class DialogButtonModel(
    @StringRes val text: Int = android.R.string.ok,
    val action: (context: Context) -> Unit = {}
): Parcelable
