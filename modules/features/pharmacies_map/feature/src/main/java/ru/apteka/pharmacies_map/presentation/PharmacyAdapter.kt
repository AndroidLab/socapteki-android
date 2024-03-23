package ru.apteka.pharmacies_map.presentation

import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.data.models.PharmacyCardModel
import ru.apteka.components.data.utils.setExtraMargin
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.pharmacies_map.databinding.PharmacyItemHolderBinding
import ru.apteka.pharmacies_map_api.api.TypeInteraction

/**
 * Представляет адаптер для списка аптек.
 */
class PharmacyAdapter(
    private val lifeOwner: LifecycleOwner,
    private val interaction: TypeInteraction,
    //private val viewModel: PharmaciesMapViewModel,
    private val onItemClick: (PharmacyCardModel) -> Unit,
) :
    ViewBindingDelegateAdapter<PharmacyCardModel, PharmacyItemHolderBinding>(PharmacyItemHolderBinding::inflate) {

    override fun PharmacyItemHolderBinding.onBind(
        item: PharmacyCardModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        //viewModel = _viewModel
        model = item
        typeInteraction = interaction
        pharmacyItem.setExtraMargin(0, 6, 0, 0)
        /*pharmacyItem.setOnClickListener {
            onItemClick(item)
        }*/
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is PharmacyCardModel

    override fun PharmacyCardModel.getItemId() = pharmacy.id
}