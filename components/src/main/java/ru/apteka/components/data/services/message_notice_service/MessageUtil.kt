package ru.apteka.components.data.services.message_notice_service

import android.text.Html
import android.widget.Toast
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.SnackBarModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import com.google.android.material.snackbar.Snackbar
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.utils.getSpannedFromHtml
import ru.apteka.components.data.utils.getStringFrom
import ru.apteka.components.ui.CommonDialogFragment
import ru.apteka.components.databinding.SnackbarLayoutBinding
import ru.apteka.components.ui.BottomSheet

/**
 * Показывает Snackbar.
 */
fun showSnackBar(snackBarModel: SnackBarModel) {
    Snackbar.make(snackBarModel.activity.findViewById(android.R.id.content), "", snackBarModel.duration).apply {
        (view as Snackbar.SnackbarLayout).addView(
            SnackbarLayoutBinding.inflate(snackBarModel.activity.layoutInflater).apply {
                this.imageRes = imageRes
                this.message = message
                this.subMessage = subMessage
                this.messageLevel = messageLevel
                this.actionBtnText = actionBtnText
                snackBarAction.setOnClickListener {
                    snackBarModel.onAction.invoke()
                    dismiss()
                }
            }.root
        )
    }.show()

}

/**
 * Показывает диалог.
 */
fun showCommonDialog(commonDialogModel: CommonDialogModel) {
    CommonDialogFragment.newInstance(commonDialogModel).show(commonDialogModel.fragmentManager, "")
}

/**
 * Показывает Toast.
 */
fun showToast(toastModel: ToastModel) {
    val _text = toastModel.run {
        context.getStringFrom(message.message)
    }
    Toast.makeText(toastModel.context, if (toastModel.message.isHtml) {
        getSpannedFromHtml(_text)
    } else {
        _text
    }, toastModel.duration).show()
}

/**
 * Показывает BottomSheet.
 */
fun showBottomSheet(commonBottomSheet: CommonBottomSheetModel) {
    BottomSheet.newInstance(
        commonBottomSheet.bottomSheetModel
    ).apply {
        show(commonBottomSheet.fragmentManager, BottomSheet.TAG)
    }
}