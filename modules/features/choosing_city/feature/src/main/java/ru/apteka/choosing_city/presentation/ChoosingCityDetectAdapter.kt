package ru.apteka.choosing_city.presentation


import androidx.lifecycle.LifecycleOwner
import ru.apteka.choosing_city.data.model.CityCardDetectModel
import ru.apteka.choosing_city.databinding.ChoosingCityHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.choosing_city.data.model.CityCardModel
import ru.apteka.choosing_city.databinding.ChoosingCityDetectHolderBinding


/**
 * Представляет адаптер для выбора автоопределения города.
 */
class ChoosingCityDetectAdapter(private val _lifecycleOwner: LifecycleOwner) :
    ViewBindingDelegateAdapter<CityCardDetectModel, ChoosingCityDetectHolderBinding>(
        ChoosingCityDetectHolderBinding::inflate
    ) {

    override fun ChoosingCityDetectHolderBinding.onBind(
        item: CityCardDetectModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is CityCardDetectModel

    override fun CityCardDetectModel.getItemId() = onItemClick
}