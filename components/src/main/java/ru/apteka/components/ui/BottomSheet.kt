package ru.apteka.components.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.apteka.components.R
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.screenHeight


/**
 * Представляет нижнюю таблицу.
 */
class BottomSheet private constructor() : BottomSheetDialogFragment() {

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
    ): View = getBottomSheetContainer(inflater).root.also {
        it.findViewById<ViewGroup>(R.id.bottomSheet).addView(
            bottomSheetModel.binding.root
        )
        dialog?.setOnShowListener { dialog ->
            val bottomSheetInternal = (dialog as BottomSheetDialog)
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheetInternal!!)
            behavior.peekHeight = screenHeight
            bottomSheetModel.onBottomSheetBehavior(behavior)
            /*BottomSheetBehavior.from(bottomSheetInternal!!).state =
                BottomSheetBehavior.STATE_EXPANDED*/
        }
    }

    private fun getBottomSheetContainer(
        inflater: LayoutInflater
    ): ViewDataBinding =
        if (bottomSheetModel.useScrollableContainer)
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_scrollable, null, false)
        else
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, null, false)

    override fun onPause() {
        this.dismiss()
        super.onPause()
    }

}