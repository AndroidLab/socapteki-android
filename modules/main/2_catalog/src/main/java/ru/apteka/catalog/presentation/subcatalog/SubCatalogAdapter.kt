package ru.apteka.catalog.presentation.subcatalog


import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.catalog.data.models.CatalogItem
import ru.apteka.catalog.databinding.SubCatalogItemHolderBinding


/**
 * Представляет адаптер для подкаталога.
 */
class SubCatalogAdapter(private val onItemClick: (CatalogItem) -> Unit) :
    ViewBindingDelegateAdapter<CatalogItem, SubCatalogItemHolderBinding>(SubCatalogItemHolderBinding::inflate) {

    override fun SubCatalogItemHolderBinding.onBind(
        item: CatalogItem, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
        subCatalogItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is CatalogItem

    override fun CatalogItem.getItemId() = title
}