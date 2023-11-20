package ru.apteka.brands.presentation


import ru.apteka.brands.data.model.BrandModel
import ru.apteka.brands.databinding.LettersCardHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для карточки бренда или производителя.
 */
class LettersCardAdapter(private val onAllByLetter: (letter: String) -> Unit) :
    ViewBindingDelegateAdapter<BrandModel, LettersCardHolderBinding>(LettersCardHolderBinding::inflate) {

    override fun LettersCardHolderBinding.onBind(
        item: BrandModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item

        executePendingBindings()
        allBrandsByLetter.setOnClickListener {
            onAllByLetter(item.title)
        }
    }

    override fun isForViewType(item: Any) = item is BrandModel

    override fun BrandModel.getItemId() = title
}