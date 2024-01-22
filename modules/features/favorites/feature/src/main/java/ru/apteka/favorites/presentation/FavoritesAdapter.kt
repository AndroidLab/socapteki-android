package ru.apteka.favorites.presentation

import androidx.lifecycle.LifecycleOwner
import ru.apteka.components.ui.delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.favorites.data.model.FavoriteCardModel
import ru.apteka.favorites.databinding.FavoriteCardHolferBinding


/**
 * Представляет адаптер для избранного.
 */
class FavoritesAdapter(
    private val _lifecycleOwner: LifecycleOwner,
    private val onItemClick: () -> Unit
) :
    ViewBindingDelegateAdapter<FavoriteCardModel, FavoriteCardHolferBinding>(FavoriteCardHolferBinding::inflate) {

    override fun FavoriteCardHolferBinding.onBind(
        item: FavoriteCardModel, position: Int,
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

    override fun isForViewType(item: Any) = item is FavoriteCardModel

    override fun FavoriteCardModel.getItemId() = product.id
}