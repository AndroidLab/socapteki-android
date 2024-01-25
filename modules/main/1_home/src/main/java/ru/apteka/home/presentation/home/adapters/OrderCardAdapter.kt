package ru.apteka.home.presentation.home.adapters


import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.databinding.OrderCardHolderBinding


/**
 * Представляет адаптер для карточки заказа.
 */
class OrderCardAdapter(private val onItemClick: (OrderModel) -> Unit) :
    ViewBindingDelegateAdapter<OrderModel, OrderCardHolderBinding>(OrderCardHolderBinding::inflate) {

    override fun OrderCardHolderBinding.onBind(
        item: OrderModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        val itemWidth = (screenWidth * .85).toInt()

        val lp = root.layoutParams as RecyclerView.LayoutParams
        lp.width = itemWidth
        root.layoutParams = lp

        model = item
        executePendingBindings()
        /*orderCardItem.setOnClickListener {
            onItemClick(item)
        }*/
    }

    override fun isForViewType(item: Any) = item is OrderModel

    override fun OrderModel.getItemId() = id
}