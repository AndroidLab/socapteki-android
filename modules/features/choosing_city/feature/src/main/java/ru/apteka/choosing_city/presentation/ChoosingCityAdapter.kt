package ru.apteka.choosing_city.presentation

import androidx.lifecycle.LifecycleOwner
import ru.apteka.choosing_city.data.model.CityCardModel
import ru.apteka.choosing_city.databinding.ChoosingCityHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter

/**
 * Представляет адаптер для выбора города.
 */
class ChoosingCityAdapter(private val lifeOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<CityCardModel, ChoosingCityHolderBinding>(ChoosingCityHolderBinding::inflate) {

    override fun ChoosingCityHolderBinding.onBind(
        item: CityCardModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is CityCardModel

    override fun CityCardModel.getItemId() = city.id
}
