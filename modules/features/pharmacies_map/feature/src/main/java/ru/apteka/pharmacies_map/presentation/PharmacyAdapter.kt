package ru.apteka.pharmacies_map.presentation


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.pharmacies_map.data.model.PharmacyModel
import ru.apteka.pharmacies_map.databinding.PharmacieItemHolderBinding


/**
 * Представляет адаптер для списка аптек.
 */
class PharmacyAdapter(private val onItemClick: (PharmacyModel) -> Unit) :
    ViewBindingDelegateAdapter<PharmacyModel, PharmacieItemHolderBinding>(PharmacieItemHolderBinding::inflate) {

    override fun PharmacieItemHolderBinding.onBind(item: PharmacyModel) {
        model = item
        pharmacyItem.setOnClickListener {
            onItemClick(item)
        }
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is PharmacyModel

    override fun PharmacyModel.getItemId() = id
}