package ru.apteka.components.ui.adapters


import android.view.ViewGroup
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.databinding.ProductCardViewBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для карточки продукта.
 */
class ProductCardViewAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<ProductCardModel, ProductCardViewBinding>(ProductCardViewBinding::inflate) {

    override fun ProductCardViewBinding.onBind(item: ProductCardModel) {
        val itemWidth = (screenWidth - 16.dp*2 - 8.dp) / 2
        root.layoutParams = ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        model = item
        executePendingBindings()
        productCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is ProductCardModel

    override fun ProductCardModel.getItemId() = product.id
}