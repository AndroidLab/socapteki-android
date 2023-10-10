package ru.apteka.common.data.services.message_notice_service.models

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


/**
 * Представляет модель для показа общего диалога.
 * @param fragmentManager Менеджер фрагментов.
 * @param dialogModel Модель диалога.
 */
@Parcelize
data class CommonDialogModel(
    val fragmentManager: @RawValue FragmentManager,
    val dialogModel: DialogModel
): Parcelable
