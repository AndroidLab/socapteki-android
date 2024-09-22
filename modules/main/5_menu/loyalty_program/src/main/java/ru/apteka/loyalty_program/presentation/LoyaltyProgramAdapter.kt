package ru.apteka.loyalty_program.presentation


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.loyalty_program.databinding.LoyaltyProgramHolderBinding
import java.util.UUID


/**
 * Представляет адаптер для карточки рекламы.
 */
class LoyaltyProgramAdapter :
    ViewBindingDelegateAdapter<Unit, LoyaltyProgramHolderBinding>(LoyaltyProgramHolderBinding::inflate) {

    override fun LoyaltyProgramHolderBinding.onBind(
        item: Unit, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        //model = item

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is Unit

    override fun Unit.getItemId(): UUID = UUID.randomUUID()
}