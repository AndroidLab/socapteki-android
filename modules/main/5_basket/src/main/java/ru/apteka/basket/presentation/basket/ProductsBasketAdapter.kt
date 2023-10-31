package ru.apteka.basket.presentation.basket


import androidx.lifecycle.LifecycleOwner
import ru.apteka.basket.databinding.BasketProductCardHolderBinding
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.basket_service.models.BasketProductCardModel
import ru.apteka.components.databinding.ProductCardViewBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для продукта в корзине.
 */
class ProductsBasketAdapter(
    private val lifeOwner: LifecycleOwner
) :
    ViewBindingDelegateAdapter<BasketProductCardModel, BasketProductCardHolderBinding>(BasketProductCardHolderBinding::inflate) {

    override fun BasketProductCardHolderBinding.onBind(item: BasketProductCardModel) {
        lifecycleOwner = lifeOwner
        basketProductCardCounter.lifecycleOwner = lifeOwner
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is BasketProductCardModel

    override fun BasketProductCardModel.getItemId() = productCard.product.id
}