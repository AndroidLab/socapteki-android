package ru.apteka.favorites.presentation.favorites.presentation.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.common.data.RequestHandler
import ru.apteka.common.data.utils.launchIO
import ru.apteka.common.ui.BaseViewModel
import ru.apteka.components.data.services.BasketService
import ru.apteka.favorites.presentation.favorites.data.models.FavoritesCardModel
import ru.apteka.favorites.presentation.favorites.domain.favorites.FavoritesCodeUseCase
import javax.inject.Inject


/**
 * Представляет модель представления "Подтверждение авторизации".
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val favoritesCodeUseCase: FavoritesCodeUseCase,
    val basketService: BasketService
) : BaseViewModel() {

    private val _favorites = MutableLiveData<List<FavoritesCardModel>>(emptyList())

    /**
     * Возвращает список избранного.
     */
    val favorites: LiveData<List<FavoritesCardModel>> = _favorites

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { favoritesCodeUseCase.execute() },
                onSuccess = { favorites ->
                    _favorites.postValue(
                        favorites.map { favorite ->
                            FavoritesCardModel(
                                favorite = favorite,
                                onFavoriteClick = { view, favoriteCard ->
                                    Log.d("myL", "onFavoriteClick " + favorite)
                                },
                                onBaskedClick = { view, favoriteCard ->
                                    val isAddedInBasked =
                                        basketService.isContainsInBasket(favorite.id)
                                    if (isAddedInBasked) {
                                        basketService.removeProduct(favorite.id)
                                        favoriteCard.isAddedInBasket.value = false
                                    } else {
                                        basketService.addProduct(favorite.id)
                                        favoriteCard.isAddedInBasket.value = true
                                    }
                                }
                            )
                        }.onEach { favoriteCard ->
                            favoriteCard.isAddedInBasket.postValue(
                                basketService.isContainsInBasket(
                                    favoriteCard.favorite.id
                                )
                            )
                        }
                    )
                },
                isLoading = _isLoading
            )
        }
    }

}