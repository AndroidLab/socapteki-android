package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.ProductCardModel
import ru.apteka.home.databinding.ProductCardViewBinding


/**
 * Представляет адаптер для карточки продукта дня.
 */
class ProductCardViewAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<ProductCardModel, ProductCardViewBinding>(ProductCardViewBinding::inflate) {

    override fun ProductCardViewBinding.onBind(item: ProductCardModel) {
        model = item
        executePendingBindings()
        productCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is ProductCardModel

    override fun ProductCardModel.getItemId() = product.id
}