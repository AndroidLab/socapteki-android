package ru.apteka.home.data.models

import androidx.lifecycle.MutableLiveData


/**
 * Представляет модель для карточки продукта.
 * @param product Продукт.
 * @param onFavoriteClick Обработчик нажатия на избранном.
 * @param onByeOneClick Обработчик нажатия на покупке в один клик.
 * @param isFavoriteLiveData Флаг добавления в избранное.
 */
data class ProductCardModel(
    val product: ProductModel,
    val onFavoriteClick: (ProductCardModel) -> Unit,
    val onByeOneClick: (ProductCardModel) -> Unit,
    val isFavoriteLiveData: MutableLiveData<Boolean> = MutableLiveData(product.isFavorite)
)
