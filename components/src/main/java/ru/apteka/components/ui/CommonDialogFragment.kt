package ru.apteka.components.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlin.properties.Delegates
import androidx.fragment.app.DialogFragment
import ru.apteka.components.R
import ru.apteka.components.databinding.CommonDialogFragmentBinding


/**
 * Представляет общий диалог.
 */
class CommonDialogFragment : DialogFragment() {

    companion object {
        private const val COMMON_DIALOG_MODEL = "COMMON_DIALOG_MODEL"

        /**
         * Возвращает экземпляр общего диалога.
         */
        fun newInstance(commonDialogModel: ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel) =
            CommonDialogFragment().apply {
                arguments = bundleOf(
                    COMMON_DIALOG_MODEL to commonDialogModel
                )
            }
    }

    private var _binding by Delegates.notNull<CommonDialogFragmentBinding>()

    private val commonDialogModel
            get() = (arguments?.getParcelable(COMMON_DIALOG_MODEL) as? ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel)!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<CommonDialogFragmentBinding?>(
        layoutInflater,
        R.layout.common_dialog_fragment,
        container,
        true
    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = commonDialogModel.dialogModel.isCancelable ?: true
        _binding.apply {
            lifecycleOwner = viewLifecycleOwner
            val _buttonCancel = commonDialogModel.dialogModel.buttonCancel
            val _buttonConfirm = commonDialogModel.dialogModel.buttonConfirm
            val bodyContent = commonDialogModel.dialogModel.bodyContent
            imageRes = commonDialogModel.dialogModel.imageRes
            title = commonDialogModel.dialogModel.title
            dialogMessage = commonDialogModel.dialogModel.message
            isCustomContent = bodyContent != null
            buttonCancel = _buttonCancel
            buttonConfirm = _buttonConfirm
            if (bodyContent != null) {
                DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater,
                    bodyContent.layoutId,
                    commonDialogContainer,
                    true
                ).also {
                    it.lifecycleOwner = viewLifecycleOwner
                    bodyContent.onLayoutInflate(this@CommonDialogFragment, it)
                }
            }
            commonDialogCancelButton.setOnClickListener {
                _buttonCancel?.action?.invoke(requireContext())
                dismiss()
            }
            commonDialogConfirmButton.setOnClickListener {
                _buttonConfirm?.action?.invoke(requireContext())
                dismiss()
            }
        }
    }

    override fun onResume() {
        val layout = commonDialogModel.dialogModel.layout
        dialog?.window?.setLayout(
            layout.width,
            layout.height
        )
        super.onResume()
    }

    override fun onPause() {
        this.dismiss()
        super.onPause()
    }

    override fun onDismiss(dialog: DialogInterface) {
        commonDialogModel.dialogModel.onDismiss.invoke()
        super.onDismiss(dialog)
    }

    /**
     * Представляет сообщение для диалога.
     * @param message Сообщение.
     * @param isHtml Флаг является ли текст html.
     */
    @Parcelize
    data class CommonDialogMessage(
        val message: @RawValue Any = "",
        val isHtml: Boolean = false
    ) : Parcelable

    /**
     * Представляет расположение диалога.
     * @param width Ширина диалога.
     * @param height Высота диалога.
     */
    @Parcelize
    data class CommonDialogLayout(
        val width: Int,
        val height: Int
    ) : Parcelable

}