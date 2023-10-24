package ru.apteka.components.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ru.apteka.components.R
import ru.apteka.components.data.utils.screenHeight
import ru.apteka.components.databinding.BottomSheetBinding


/**
 * Представляет нижнюю таблицу.
 */
class BottomSheet private constructor() : BottomSheetDialogFragment() {

    @Parcelize
    data class BottomSheetModel(
        val binding: @RawValue ViewDataBinding
    ) : Parcelable

    companion object {
        const val TAG = "ModalBottomSheet"

        /**
         * Возвращает экземпляр нижней таблицы.
         */
        fun newInstance(bottomSheetModel: BottomSheetModel) =
            BottomSheet().apply {
                arguments = bundleOf(
                    TAG to bottomSheetModel
                )
            }
    }

    private val bottomSheetModel
        get() = (arguments?.getParcelable(TAG) as? BottomSheetModel)!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomSheetBinding.inflate(inflater, null, false).bottomSheetScroll.apply {
        addView(
            bottomSheetModel.binding.root
        )
    }.also {
        dialog?.setOnShowListener { dialog ->
            val bottomSheetInternal = (dialog as BottomSheetDialog)
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).peekHeight = screenHeight
            /*BottomSheetBehavior.from(bottomSheetInternal!!).state =
                BottomSheetBehavior.STATE_EXPANDED*/
        }
    }


}