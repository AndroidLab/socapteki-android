package ru.apteka.components.data.services.message_notice_service.models

import android.content.Context
import android.os.Parcelable
import androidx.annotation.ColorRes
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
    @ColorRes val textColor: Int? = null,
    @ColorRes val backgroundColor: Int? = null,
    @ColorRes val borderColor: Int? = null,
    val action: (context: Context) -> Unit = {}
) : Parcelable
