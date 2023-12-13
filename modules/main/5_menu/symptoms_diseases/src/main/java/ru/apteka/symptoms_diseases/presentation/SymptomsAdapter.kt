package ru.apteka.symptoms_diseases.presentation


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.symptoms_diseases.data.model.SymptomModel
import ru.apteka.symptoms_diseases.databinding.SymptomsHolderBinding


/**
 * Представляет адаптер для симптомов.
 */
class SymptomsAdapter(private val onItemClick: (item: SymptomModel) -> Unit) :
    ViewBindingDelegateAdapter<SymptomModel, SymptomsHolderBinding>(SymptomsHolderBinding::inflate) {

    override fun SymptomsHolderBinding.onBind(
        item: SymptomModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item

        executePendingBindings()
        llSymptomsItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is SymptomModel

    override fun SymptomModel.getItemId() = id
}