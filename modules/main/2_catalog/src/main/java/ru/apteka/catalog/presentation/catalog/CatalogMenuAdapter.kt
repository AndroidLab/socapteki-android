package ru.apteka.catalog.presentation.catalog


import ru.apteka.catalog.databinding.CatalogMenuItemBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.catalog.data.models.CatalogMenuItem


/**
 * Представляет адаптер для карточки продукта дня.
 */
class CatalogMenuAdapter(private val onItemClick: (CatalogMenuItem) -> Unit) :
    ViewBindingDelegateAdapter<CatalogMenuItem, CatalogMenuItemBinding>(CatalogMenuItemBinding::inflate) {

    override fun CatalogMenuItemBinding.onBind(item: CatalogMenuItem) {
        model = item
        executePendingBindings()
        catalogMenuItem.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun isForViewType(item: Any) = item is CatalogMenuItem

    override fun CatalogMenuItem.getItemId() = title
}