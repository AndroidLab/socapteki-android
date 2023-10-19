package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.home.databinding.AdvertCardViewSceletonBinding


/**
 * Представляет адаптер для карточки скелетона рекламы.
 */
class AdvertCardViewSkeletonAdapter() :
    ViewBindingDelegateAdapter<Skeleton, AdvertCardViewSceletonBinding>(
        AdvertCardViewSceletonBinding::inflate
    ) {

    override fun AdvertCardViewSceletonBinding.onBind(item: Skeleton) {}

    override fun isForViewType(item: Any) = item is Skeleton

    override fun Skeleton.getItemId() = Unit
}