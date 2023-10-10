package ru.apteka.favorites.presentation.favorites.presentation.favorites

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import ru.apteka.common.data.composite_delegate_adapter.ViewBindingDelegateAdapter
import ru.apteka.favorites.databinding.FavoriteCardViewBinding
import ru.apteka.favorites.presentation.favorites.data.models.FavoritesCardModel


/**
 * Представляет адаптер для карточки избранного.
 */
class FavoriteCardViewAdapter(private val _lifecycleOwner: LifecycleOwner) : ViewBindingDelegateAdapter<FavoritesCardModel, FavoriteCardViewBinding>(FavoriteCardViewBinding::inflate) {

    override fun FavoriteCardViewBinding.onBind(item: FavoritesCardModel) {
        lifecycleOwner = _lifecycleOwner
        model = item
        executePendingBindings()
    }

    override fun isForViewType(item: Any) = item is FavoritesCardModel

    override fun FavoritesCardModel.getItemId() = favorite.id
}