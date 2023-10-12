package ru.apteka.home.presentation.home.adapters


import ru.apteka.common.data.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.databinding.OtherCardViewBinding


/**
 * Представляет адаптер для карточки стального.
 */
class OtherCardViewAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<OtherModel, OtherCardViewBinding>(OtherCardViewBinding::inflate) {

    override fun OtherCardViewBinding.onBind(item: OtherModel) {
        model = item
        executePendingBindings()
        otherCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is OtherModel

    override fun OtherModel.getItemId() = id
}