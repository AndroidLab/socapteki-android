package ru.apteka.stocks.presentation.stocks


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.components.data.models.StockModel
import ru.apteka.stocks.databinding.StockHolderBinding


/**
 * Представляет адаптер для акций.
 */
class StocksAdapter(private val onItemClick: (StockModel) -> Unit) :
    ViewBindingDelegateAdapter<StockModel, StockHolderBinding>(StockHolderBinding::inflate) {

    override fun StockHolderBinding.onBind(
        item: StockModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        llStockItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is StockModel

    override fun StockModel.getItemId() = id
}