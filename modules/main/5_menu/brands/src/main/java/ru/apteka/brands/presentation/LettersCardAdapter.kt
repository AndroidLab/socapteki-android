package ru.apteka.brands.presentation


import androidx.lifecycle.LifecycleOwner
import ru.apteka.brands.data.model.BrandModel
import ru.apteka.brands.databinding.LettersCardHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для карточки бренда или производителя.
 */
class LettersCardAdapter(val lifeOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<BrandModel, LettersCardHolderBinding>(LettersCardHolderBinding::inflate) {

    override fun LettersCardHolderBinding.onBind(
        item: BrandModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        model = item

        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is BrandModel

    override fun BrandModel.getItemId() = title
}