package ru.apteka.favorites.presentation.favorites.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.favorites.presentation.favorites.data.models.FavoritesCardModel
import ru.apteka.favorites.presentation.favorites.domain.favorites.FavoritesCodeUseCase
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Избранное".
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val favoritesCodeUseCase: FavoritesCodeUseCase,
    private val basketService: BasketService,
    navigationManager: NavigationManager,
    accountsPreferences: AccountsPreferences
) : MainScreenBaseViewModel(
    accountsPreferences,
    navigationManager
) {

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