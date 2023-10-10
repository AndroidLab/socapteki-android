package ru.apteka.common.data.services.message_notice_service.models

import android.os.Parcelable
import android.view.ViewGroup
import ru.apteka.common.ui.CommonDialogFragment
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ru.apteka.common.data.utils.dp

/**
 * Представляет данные для показа диалога.
 */
@Parcelize
data class DialogModel(
    val imageRes: Int = -1,
    val title: @RawValue Any = "",
    val message: CommonDialogFragment.CommonDialogMessage = CommonDialogFragment.CommonDialogMessage(),
    val bodyContent: BodyContentModel? = null,
    val buttonCancel: DialogButtonModel? = null,
    val buttonConfirm: DialogButtonModel? = null,
    val isCancelable: Boolean = true,
    val layout: CommonDialogFragment.CommonDialogLayout = CommonDialogFragment.CommonDialogLayout(
        width = 320.dp,
        height = ViewGroup.LayoutParams.WRAP_CONTENT
    ),
    val onDismiss: () -> Unit = {}
): Parcelable