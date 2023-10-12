package ru.apteka.new_module.presentation


import androidx.lifecycle.LifecycleOwner
import ru.apteka.choosing_city.databinding.ChoosingCityViewBinding
import ru.apteka.common.data.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.new_module.data.model.CityCardModel


/**
 * Представляет адаптер для выбора города.
 */
class ChoosingCityAdapter(private val _lifecycleOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<CityCardModel, ChoosingCityViewBinding>(ChoosingCityViewBinding::inflate) {

    override fun ChoosingCityViewBinding.onBind(item: CityCardModel) {
        lifecycleOwner = _lifecycleOwner
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is CityCardModel

    override fun CityCardModel.getItemId() = city.id
}