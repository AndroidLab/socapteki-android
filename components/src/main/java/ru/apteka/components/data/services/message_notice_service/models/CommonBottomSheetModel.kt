package ru.apteka.components.data.services.message_notice_service.models

import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


/**
 * Представляет модель для показа общей нижней панели.
 * @param fragmentManager Менеджер фрагментов.
 * @param bottomSheetModel Модель нижней.
 */
@Parcelize
data class CommonBottomSheetModel(
    val fragmentManager: @RawValue FragmentManager,
    val bottomSheetModel: BottomSheetModel
): Parcelable