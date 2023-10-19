package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.PromotionModel
import ru.apteka.home.databinding.PromotionCardViewBinding


/**
 * Представляет адаптер для карточки акции.
 */
class PromotionCardViewAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<PromotionModel, PromotionCardViewBinding>(PromotionCardViewBinding::inflate) {

    override fun PromotionCardViewBinding.onBind(item: PromotionModel) {
        model = item
        executePendingBindings()
        promotionCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is PromotionModel

    override fun PromotionModel.getItemId() = id
}