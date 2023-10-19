package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.home.databinding.OtherCardViewSceletonBinding


/**
 * Представляет адаптер для карточки скелетона акции.
 */
class OtherCardViewSkeletonAdapter() :
    ViewBindingDelegateAdapter<Skeleton, OtherCardViewSceletonBinding>(
        OtherCardViewSceletonBinding::inflate
    ) {

    override fun OtherCardViewSceletonBinding.onBind(item: Skeleton) {}

    override fun isForViewType(item: Any) = item is Skeleton

    override fun Skeleton.getItemId() = Unit
}