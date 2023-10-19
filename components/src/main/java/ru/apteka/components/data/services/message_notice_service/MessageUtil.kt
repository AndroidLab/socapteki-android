package ru.apteka.components.data.services.message_notice_service

import android.widget.Toast
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.SnackBarModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import com.google.android.material.snackbar.Snackbar
import ru.apteka.components.ui.CommonDialogFragment
import ru.apteka.components.databinding.SnackbarLayoutBinding

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
    Toast.makeText(toastModel.context, toastModel.message, toastModel.duration).show()
}