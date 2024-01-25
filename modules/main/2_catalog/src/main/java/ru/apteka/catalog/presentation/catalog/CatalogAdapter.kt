package ru.apteka.catalog.presentation.catalog

import ru.apteka.catalog.data.models.CatalogItem
import ru.apteka.catalog.databinding.CatalogItemHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter


/**
 * Представляет адаптер для каталога.
 */
class CatalogAdapter(private val onItemClick: (CatalogItem) -> Unit) :
    ViewBindingDelegateAdapter<CatalogItem, CatalogItemHolderBinding>(CatalogItemHolderBinding::inflate) {

    override fun CatalogItemHolderBinding.onBind(
        item: CatalogItem, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        catalogItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is CatalogItem

    override fun CatalogItem.getItemId() = title
}