package ru.apteka.product_card.presentation.product_card


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.product_card.databinding.ProductCardPriceImageHolderBinding


/**
 * Представляет адаптер для карточки рекламы.
 */
class PriceImageAdapter :
    ViewBindingDelegateAdapter<String, ProductCardPriceImageHolderBinding>(
        ProductCardPriceImageHolderBinding::inflate
    ) {

    override fun ProductCardPriceImageHolderBinding.onBind(
        item: String,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        image = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is String

    override fun String.getItemId() = this
}