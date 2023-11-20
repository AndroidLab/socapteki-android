package ru.apteka.components.data.services.message_notice_service.models

import android.os.Parcelable
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BottomSheetModel(
    val binding: @RawValue ViewDataBinding,
    val useScrollableContainer: Boolean = true,
    val onBottomSheetBehavior: (behavior: BottomSheetBehavior<View>) -> Unit = { }
) : Parcelable