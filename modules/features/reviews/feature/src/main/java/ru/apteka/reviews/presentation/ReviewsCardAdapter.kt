package ru.apteka.reviews.presentation


import ru.apteka.components.data.models.ReviewItem
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.reviews.databinding.ReviewHolderBinding


/**
 * Представляет адаптер для отзывов.
 */
class ReviewsCardAdapter(
    //private val lifeOwner: LifecycleOwner
) :
    ViewBindingDelegateAdapter<ReviewItem, ReviewHolderBinding>(ReviewHolderBinding::inflate) {

    override fun ReviewHolderBinding.onBind(
        item: ReviewItem,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        //lifecycleOwner = lifeOwner
        model = item

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is ReviewItem

    override fun ReviewItem.getItemId() = name
}