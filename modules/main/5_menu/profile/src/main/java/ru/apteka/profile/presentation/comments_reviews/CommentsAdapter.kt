package ru.apteka.profile.presentation.comments_reviews


import androidx.core.view.doOnLayout
import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.data.utils.countLines
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.profile.R
import ru.apteka.profile.data.models.CommentModel
import ru.apteka.profile.databinding.CommentHolderBinding


/**
 * Представляет адаптер для комментариев.
 */
class CommentsAdapter(private val _lifecycleOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<CommentModel, CommentHolderBinding>(CommentHolderBinding::inflate) {

    override fun CommentHolderBinding.onBind(
        item: CommentModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = _lifecycleOwner
        model = item

        commentText.doOnLayout {
            llCommentsReadCompletely.visibleIf(commentText.countLines() > 5)
        }

        llCommentsReadCompletely.setOnClickListener {
            if (tvCommentsReadCompletely.text == root.context.getString(R.string.comments_read_completely)) {
                tvCommentsReadCompletely.text = root.context.getString(R.string.comments_hide)
                commentText.maxLines = Int.MAX_VALUE
            } else {
                tvCommentsReadCompletely.text = root.context.getString(R.string.comments_read_completely)
                commentText.maxLines = 5
            }
        }

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is CommentModel

    override fun CommentModel.getItemId() = id
}