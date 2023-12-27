package ru.apteka.catalog.presentation.catalog_products

import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.databinding.CatalogProductSearchResultHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для результатов поиска продукции.
 */
class SearchResultAdapter(private val onItemClick: (SearchResultModel) -> Unit) :
    ViewBindingDelegateAdapter<SearchResultModel, CatalogProductSearchResultHolderBinding>(CatalogProductSearchResultHolderBinding::inflate) {

    override fun CatalogProductSearchResultHolderBinding.onBind(
        item: SearchResultModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        llSearchResultItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is SearchResultModel

    override fun SearchResultModel.getItemId() = text
}