package ru.apteka.orders.presentation.orders

import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.orders.databinding.OrderHolderBinding


/**
 * Представляет адаптер для заказов.
 */
class OrdersAdapter(private val onItemClick: (OrderModel) -> Unit) :
    ViewBindingDelegateAdapter<OrderModel, OrderHolderBinding>(OrderHolderBinding::inflate) {

    override fun OrderHolderBinding.onBind(
        item: OrderModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        llOrderItem.clipToOutline = true

        llOrderItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is OrderModel

    override fun OrderModel.getItemId() = number
}