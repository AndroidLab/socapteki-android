package ru.apteka.catalog.presentation.catalog_products

import ru.apteka.catalog.data.models.SearchResultHeaderModel
import ru.apteka.catalog.databinding.CatalogProductSearchResultHeaderHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для заголовка результатов поиска продукции.
 */
class SearchResultHeaderAdapter :
    ViewBindingDelegateAdapter<SearchResultHeaderModel, CatalogProductSearchResultHeaderHolderBinding>(
        CatalogProductSearchResultHeaderHolderBinding::inflate
    ) {

    override fun CatalogProductSearchResultHeaderHolderBinding.onBind(
        item: SearchResultHeaderModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
    }

    override fun isForViewType(item: Any) = item is SearchResultHeaderModel

    override fun SearchResultHeaderModel.getItemId() = Unit
}