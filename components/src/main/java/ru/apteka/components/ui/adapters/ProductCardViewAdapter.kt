package ru.apteka.components.ui.adapters


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.databinding.ProductCardViewBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для карточки продукта.
 */
class ProductCardViewAdapter(
    private val lifeOwner: LifecycleOwner,
    private val onItemClick: (ProductModel) -> Unit,
    private val isHorizontal: Boolean
) :
    ViewBindingDelegateAdapter<ProductCardModel, ProductCardViewBinding>(ProductCardViewBinding::inflate) {

    override fun ProductCardViewBinding.onBind(
        item: ProductCardModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        productCardCounter.lifecycleOwner = lifeOwner
        var itemWidth = (screenWidth / 2)

        val lp = root.layoutParams as RecyclerView.LayoutParams
        if (isHorizontal) {
            itemWidth -= itemWidth / 8
            //lp.marginStart = if (isFirst) 8.dp else 0.dp
            lp.marginEnd = if (!isLast) 8.dp else 0.dp
            lp.width = itemWidth
        } else {
            lp.width = RecyclerView.LayoutParams.MATCH_PARENT
            lp.topMargin = 6.dp
            if (position % 2 == 0) {
                lp.marginEnd = 3.dp
            } else {
                lp.marginStart = 3.dp
            }
        }
        root.layoutParams = lp
        model = item

        executePendingBindings()
        productCardItem.setOnClickListener {
            onItemClick(item.product)
        }

        /*var itemWidth = screenWidth
        val lp = root.layoutParams as RecyclerView.LayoutParams
        if (isHorizontal) {
            itemWidth -= itemWidth / 8
            lp.marginStart = if (isFirst) 8.dp else 0.dp
            lp.marginEnd = if (isLast) 8.dp else 0.dp
        } else {
            lp.topMargin = 6.dp
        }

        root.layoutParams = lp
        model = item

        executePendingBindings()
        productCardItem.setOnClickListener {
            onItemClick(item.product)
        }*/
    }

    override fun isForViewType(item: Any) = item is ProductCardModel

    override fun ProductCardModel.getItemId() = product.id
}