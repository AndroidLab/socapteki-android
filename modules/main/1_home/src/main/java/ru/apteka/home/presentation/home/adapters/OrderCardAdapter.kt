package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.OrderCardModel
import ru.apteka.home.databinding.OrderCardHolderBinding


/**
 * Представляет адаптер для карточки заказа.
 */
class OrderCardAdapter(private val onItemClick: (OrderCardModel) -> Unit) :
    ViewBindingDelegateAdapter<OrderCardModel, OrderCardHolderBinding>(OrderCardHolderBinding::inflate) {

    override fun OrderCardHolderBinding.onBind(
        item: OrderCardModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        orderCardItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is OrderCardModel

    override fun OrderCardModel.getItemId() = id
}