package ru.apteka.our_partners.presentation


import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.our_partners.databinding.OurPartnerHolderBinding


/**
 * Представляет адаптер для карточки партнеры.
 */
class OurPartnerAdapter :
    ViewBindingDelegateAdapter<Unit, OurPartnerHolderBinding>(OurPartnerHolderBinding::inflate) {

    override fun OurPartnerHolderBinding.onBind(
        item: Unit, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        //model = item
        (root.layoutParams as RecyclerView.LayoutParams).apply {
            marginStart = if (isFirst) 16.dp else marginStart
            marginEnd = if (isLast) 16.dp else marginEnd
        }

        executePendingBindings()
        /*otherCardItem.setOnClickListener {
            onItemClick()
        }*/
    }

    override fun isForViewType(item: Any) = item is Unit

    override fun Unit.getItemId() = Unit
}