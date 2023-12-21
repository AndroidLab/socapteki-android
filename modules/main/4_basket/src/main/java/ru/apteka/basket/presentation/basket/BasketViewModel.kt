package ru.apteka.basket.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Корзина".
 */
@HiltViewModel
class BasketViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val productsRepository: ProductsRepository,
    val basketService: BasketService,
    val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    private val _productsWatchedRecently = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsWatchedRecently: LiveData<List<ProductCardModel>> = _productsWatchedRecently

    private val _watchedRecentlyIsLoading = MutableLiveData(false)

    /**
     *
     */
    val watchedRecentlyIsLoading: LiveData<Boolean> = _watchedRecentlyIsLoading

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { productsRepository.getProductions() },
                onSuccess = { products ->
                    _productsWatchedRecently.postValue(
                        products.map { product ->
                            ProductCardModel(
                                product = product
                            ).apply {
                                favorite = FavoriteModel(
                                    favoriteService = favoriteService,
                                    isFavorite = product.isFavorite,
                                )
                                itemCounter = ProductCounterModel(
                                    basketService = basketService,
                                    productCard = this,
                                    countInBasket = product.countInBasket
                                )
                            }
                        }
                    )
                },
                isLoading = _watchedRecentlyIsLoading
            )
        }
    }

}