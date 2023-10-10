package ru.apteka.common.data.services.message_notice_service.models

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import java.io.Serializable

/**
 * Кастомное тело диалога.
 * @param layoutId Ид разметки.
 * @param onLayoutInflate Обработчик создания отображения.
 */
class BodyContentModel(
    @LayoutRes
    val layoutId: Int,
    val onLayoutInflate: (dialog: DialogFragment, binding: ViewDataBinding) -> Unit
): Serializable