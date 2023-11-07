package ru.apteka.catalog.presentation.catalog_products

import ru.apteka.catalog.data.models.SearchHistoryHeaderModel
import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.databinding.CatalogProductSearchHistoryHeaderHolderBinding
import ru.apteka.catalog.databinding.CatalogProductSearchResultHolderBinding
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.databinding.OrderHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для заголовка истории поиска продукции.
 */
class SearchHistoryHeaderAdapter :
    ViewBindingDelegateAdapter<SearchHistoryHeaderModel, CatalogProductSearchHistoryHeaderHolderBinding>(CatalogProductSearchHistoryHeaderHolderBinding::inflate) {

    override fun CatalogProductSearchHistoryHeaderHolderBinding.onBind(
        item: SearchHistoryHeaderModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is SearchHistoryHeaderModel

    override fun SearchHistoryHeaderModel.getItemId() = Unit
}