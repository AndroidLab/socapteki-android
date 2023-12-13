package ru.apteka.symptoms_diseases.presentation


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.symptoms_diseases.databinding.SymptomsTitleHolderBinding


/**
 * Представляет адаптер для заголовка симптома.
 */
class SymptomsTitleAdapter() :
    ViewBindingDelegateAdapter<String, SymptomsTitleHolderBinding>(SymptomsTitleHolderBinding::inflate) {

    override fun SymptomsTitleHolderBinding.onBind(
        item: String, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is String

    override fun String.getItemId() = this
}