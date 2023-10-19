package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.home.databinding.ProductCardViewSceletonBinding


/**
 * Представляет адаптер для карточки скелетона продукта.
 */
class ProductCardViewSkeletonAdapter() :
    ViewBindingDelegateAdapter<Skeleton, ProductCardViewSceletonBinding>(
        ProductCardViewSceletonBinding::inflate
    ) {

    override fun ProductCardViewSceletonBinding.onBind(item: Skeleton) {}

    override fun isForViewType(item: Any) = item is Skeleton

    override fun Skeleton.getItemId() = Unit
}
