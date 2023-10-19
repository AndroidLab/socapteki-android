package ru.apteka.home.presentation.home.adapters


import ru.apteka.components.ui.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.AdvertModel
import ru.apteka.home.databinding.AdvertCardViewBinding


/**
 * Представляет адаптер для карточки рекламы.
 */
class AdvertCardViewAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<AdvertModel, AdvertCardViewBinding>(AdvertCardViewBinding::inflate) {

    override fun AdvertCardViewBinding.onBind(item: AdvertModel) {
        model = item
        executePendingBindings()
        advertCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is AdvertModel

    override fun AdvertModel.getItemId() = id
}