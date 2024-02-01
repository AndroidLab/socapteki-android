package ru.apteka.choosing_city.presentation

import androidx.lifecycle.LifecycleOwner
import ru.apteka.choosing_city.data.model.CityCardDetectModel
import ru.apteka.choosing_city.databinding.ChoosingCityDetectHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter

/**
 * Представляет адаптер для выбора автоопределения города.
 */
class ChoosingCityDetectAdapter(private val lifeOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<CityCardDetectModel, ChoosingCityDetectHolderBinding>(
        ChoosingCityDetectHolderBinding::inflate
    ) {

    override fun ChoosingCityDetectHolderBinding.onBind(
        item: CityCardDetectModel,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = lifeOwner
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is CityCardDetectModel

    override fun CityCardDetectModel.getItemId() = onItemClick
}