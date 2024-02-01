package ru.apteka.catalog.presentation.subcatalog

import ru.apteka.catalog.databinding.SubCatalogItemTitleHolderBinding
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter

/**
 * Представляет адаптер для заголовка подкаталога.
 */
class SubCatalogTitleAdapter :
    ViewBindingDelegateAdapter<String, SubCatalogItemTitleHolderBinding>(SubCatalogItemTitleHolderBinding::inflate) {

    override fun SubCatalogItemTitleHolderBinding.onBind(
        item: String,
        position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is String

    override fun String.getItemId() = this
}
