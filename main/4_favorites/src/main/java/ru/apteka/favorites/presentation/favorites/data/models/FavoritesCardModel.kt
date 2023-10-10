package ru.apteka.favorites.presentation.favorites.data.models

import android.view.View
import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель для карточки избранного.
 * @param favorite Модель избранного.
 * @param onFavoriteClick Обработчик нажатия на иконке сердечка.
 * @param onBaskedClick Обработчик нажатия на иконке корзины.
 */
data class FavoritesCardModel(
    val favorite: FavoriteModel,
    var isAddedInBasket: MutableLiveData<Boolean> = MutableLiveData(false),
    val onFavoriteClick: (View, FavoritesCardModel) -> Unit,
    val onBaskedClick: (View, FavoritesCardModel) -> Unit
)