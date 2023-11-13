package ru.apteka.pharmacies_map.presentation


import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.components.data.models.PharmacyModel
import ru.apteka.pharmacies_map.databinding.PharmacieItemHolderBinding


/**
 * Представляет адаптер для списка аптек.
 */
class PharmacyAdapter(
    private val lifeOwner: LifecycleOwner,
    private val _viewModel: PharmaciesMapViewModel,
    private val onItemClick: (PharmacyModel) -> Unit
) :
    ViewBindingDelegateAdapter<PharmacyModel, PharmacieItemHolderBinding>(PharmacieItemHolderBinding::inflate) {

    override fun PharmacieItemHolderBinding.onBind(
        item: PharmacyModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        viewModel = _viewModel
        model = item
        pharmacyItem.setOnClickListener {
            onItemClick(item)
        }
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is PharmacyModel

    override fun PharmacyModel.getItemId() = id
}