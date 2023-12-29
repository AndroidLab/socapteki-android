package ru.apteka.home.presentation.home.adapters


import androidx.recyclerview.widget.RecyclerView
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.databinding.CategoriesHolderBinding


/**
 * Представляет адаптер для карточки стального.
 */
class CategoriesAdapter(private val onItemClick: () -> Unit) :
    ViewBindingDelegateAdapter<OtherModel, CategoriesHolderBinding>(CategoriesHolderBinding::inflate) {

    override fun CategoriesHolderBinding.onBind(
        item: OtherModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        val itemWidth = (screenWidth * .8).toInt()

        val lp = root.layoutParams as RecyclerView.LayoutParams
        lp.width = itemWidth
        root.layoutParams = lp

        model = item
        executePendingBindings()
        categoriesItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is OtherModel

    override fun OtherModel.getItemId() = id
}