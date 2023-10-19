package ru.apteka.components.ui.orders

import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.databinding.OrderHolderBinding
import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для заказов.
 */
class OrdersAdapter(private val onItemClick: (OrderModel) -> Unit) :
    ViewBindingDelegateAdapter<OrderModel, OrderHolderBinding>(OrderHolderBinding::inflate) {

    override fun OrderHolderBinding.onBind(item: OrderModel) {
        model = item
        executePendingBindings()
        llOrderItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is OrderModel

    override fun OrderModel.getItemId() = number
}