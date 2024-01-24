package ru.apteka.feedback.presentation


import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.feedback.data.FileModel
import ru.apteka.feedback.databinding.FeedbackFileHolderBinding


/**
 * Представляет адаптер для списка файлов.
 */
class FeedbackFilesAdapter(
    private val lifeOwner: LifecycleOwner,
) :
    ViewBindingDelegateAdapter<FileModel, FeedbackFileHolderBinding>(
        FeedbackFileHolderBinding::inflate
    ) {

    override fun FeedbackFileHolderBinding.onBind(
        item: FileModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        val lp = root.layoutParams as RecyclerView.LayoutParams

        lp.width = RecyclerView.LayoutParams.MATCH_PARENT
        lp.topMargin = 6.dp
        lp.marginStart = 8.dp

        root.layoutParams = lp
        model = item

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is FileModel

    override fun FileModel.getItemId() = name
}