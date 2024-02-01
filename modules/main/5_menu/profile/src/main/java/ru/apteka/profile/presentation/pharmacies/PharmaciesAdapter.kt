package ru.apteka.profile.presentation.pharmacies


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.profile.data.models.ProfilePharmacyCardModel
import ru.apteka.profile.databinding.ProfilePharmacyHolderBinding


/**
 * Представляет адаптер для спика аптек.
 */
class PharmaciesAdapter :
    ViewBindingDelegateAdapter<ProfilePharmacyCardModel, ProfilePharmacyHolderBinding>(ProfilePharmacyHolderBinding::inflate) {

    override fun ProfilePharmacyHolderBinding.onBind(
        item: ProfilePharmacyCardModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is ProfilePharmacyCardModel

    override fun ProfilePharmacyCardModel.getItemId() = pharmacy.id
}