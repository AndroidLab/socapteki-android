package ru.apteka.profile.presentation.comments_reviews.pages.feedback


import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.profile.data.models.FeedbackModel
import ru.apteka.profile.databinding.FeedbackHolderBinding


/**
 * Представляет адаптер для обратной связи.
 */
class FeedbacksAdapter(private val lifeOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<FeedbackModel, FeedbackHolderBinding>(FeedbackHolderBinding::inflate) {

    override fun FeedbackHolderBinding.onBind(
        item: FeedbackModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        model = item

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is FeedbackModel

    override fun FeedbackModel.getItemId() = id
}