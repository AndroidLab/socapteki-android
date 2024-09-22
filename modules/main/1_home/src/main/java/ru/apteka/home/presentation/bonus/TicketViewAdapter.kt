package ru.apteka.home.presentation.bonus


import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.BonusTicketModel
import ru.apteka.home.databinding.BonusTicketViewBinding


/**
 * Представляет адаптер для карточки продукта.
 */
class TicketViewAdapter(
    private val lifeOwner: LifecycleOwner,
    private val onItemClick: (BonusTicketModel) -> Unit
) :
    ViewBindingDelegateAdapter<BonusTicketModel, BonusTicketViewBinding>(BonusTicketViewBinding::inflate) {

    override fun BonusTicketViewBinding.onBind(
        item: BonusTicketModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner

        val lp = root.layoutParams as RecyclerView.LayoutParams
        lp.width = RecyclerView.LayoutParams.MATCH_PARENT
        lp.topMargin = 6.dp
        if (position % 2 == 0) {
            lp.marginEnd = 3.dp
        } else {
            lp.marginStart = 3.dp
        }

        root.layoutParams = lp
        model = item

        executePendingBindings()
        bonusTicketItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is BonusTicketModel

    override fun BonusTicketModel.getItemId() = uuid
}