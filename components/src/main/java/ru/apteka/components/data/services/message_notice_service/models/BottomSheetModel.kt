package ru.apteka.components.data.services.message_notice_service.models

import android.os.Parcelable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BottomSheetModel(
    @LayoutRes val layoutId: Int? = null,
    val binding: @RawValue ViewDataBinding? = null,
    val onLayoutInflate: ((ViewDataBinding, BottomSheetDialogFragment, BottomSheetBehavior<View>) -> Unit)? = null, //binding, dialog, behavior
    val useScrollableContainer: Boolean = true,
) : Parcelable