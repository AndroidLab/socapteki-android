package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.home.databinding.PromotionCardViewSceletonBinding


/**
 * Представляет адаптер для карточки скелетона акции.
 */
class PromotionCardViewSkeletonAdapter() :
    ViewBindingDelegateAdapter<Skeleton, PromotionCardViewSceletonBinding>(
        PromotionCardViewSceletonBinding::inflate
    ) {

    override fun PromotionCardViewSceletonBinding.onBind(item: Skeleton) {}

    override fun isForViewType(item: Any) = item is Skeleton

    override fun Skeleton.getItemId() = Unit
}