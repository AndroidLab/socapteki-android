package ru.apteka.components.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.apteka.components.R
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.screenHeight
import java.lang.IllegalArgumentException

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
    ): View = getBottomSheetContainer(inflater).root.also { view ->
        val binding = (bottomSheetModel.layoutId?.let { layoutId ->
            DataBindingUtil.inflate(
                inflater,
                layoutId,
                view.findViewById(R.id.bottomSheet),
                true
            )
        } ?: view.findViewById<ViewGroup>(R.id.bottomSheet).run {
            bottomSheetModel.binding?.let {
                it.apply {
                    addView(
                        it.root
                    )
                }
            }
                ?: throw IllegalArgumentException("Необходимо передать параметр 'layoutId' или 'binding'")
        }).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        dialog?.setOnShowListener { dialog ->
            val bottomSheetInternal = (dialog as BottomSheetDialog)
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheetInternal!!)

            var offset: Float? = null
            var needDismiss = false

            behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                // offset 0 -полностью открыто -1 -полностью скрыто
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_SETTLING) {
                        if (offset!! < -0.2) {
                            behavior.setState(BottomSheetBehavior.STATE_HIDDEN)
                            needDismiss = true
                        }
                    }
                    // STATE_HIDDEN, STATE_COLLAPSED, STATE_EXPANDED - выпадают рандомно
                    if (needDismiss && (newState == BottomSheetBehavior.STATE_HIDDEN || newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_EXPANDED)) {
                        needDismiss = false
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    offset = slideOffset
                }
            })

            behavior.peekHeight = screenHeight
            bottomSheetModel.onLayoutInflate?.invoke(
                binding,
                this,
                behavior
            )
            /*BottomSheetBehavior.from(bottomSheetInternal!!).state =
                BottomSheetBehavior.STATE_EXPANDED*/
        }
    }

    override fun onResume() {
        super.onResume()
        if (bottomSheetModel.flagDimBehind) {
            dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    private fun getBottomSheetContainer(
        inflater: LayoutInflater
    ): ViewDataBinding =
        if (bottomSheetModel.useScrollableContainer) {
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_scrollable, null, false)
        } else {
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, null, false)
        }

    override fun onPause() {
        this.dismiss()
        super.onPause()
    }

    override fun onCancel(dialog: DialogInterface) {
        bottomSheetModel.onCancel?.invoke(dialog)
        super.onCancel(dialog)
    }
}
