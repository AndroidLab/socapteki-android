package ru.apteka.favorites.presentation.favorites.presentation.favorites

import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.favorites.databinding.FavoriteCardViewBinding


/**
 * Представляет адаптер для карточки избранного.
 */
class FavoriteCardViewAdapter(
    private val _lifecycleOwner: LifecycleOwner,
    private val onItemClick: () -> Unit
) :
    ViewBindingDelegateAdapter<ProductCardModel, FavoriteCardViewBinding>(FavoriteCardViewBinding::inflate) {

    override fun FavoriteCardViewBinding.onBind(
        item: ProductCardModel, position: Int,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        lifecycleOwner = _lifecycleOwner

        model = item
        executePendingBindings()
        favoriteCardItem.setOnClickListener {
            onItemClick()
        }
    }

    override fun isForViewType(item: Any) = item is ProductCardModel

    override fun ProductCardModel.getItemId() = product.id
}