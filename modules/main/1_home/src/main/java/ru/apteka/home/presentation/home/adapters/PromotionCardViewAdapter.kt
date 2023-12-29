package ru.apteka.home.presentation.home.adapters


import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.PromotionModel
import ru.apteka.home.databinding.PromotionCardViewBinding


/**
 * Представляет адаптер для карточки акции.
 */
class PromotionCardViewAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<PromotionModel, PromotionCardViewBinding>(PromotionCardViewBinding::inflate) {

    override fun PromotionCardViewBinding.onBind(
        item: PromotionModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        val itemWidth = (screenWidth * .8).toInt()

        val lp = root.layoutParams as RecyclerView.LayoutParams
        lp.width = itemWidth
        root.layoutParams = lp

        model = item
        executePendingBindings()
        promotionCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is PromotionModel

    override fun PromotionModel.getItemId() = id
}