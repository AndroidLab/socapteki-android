package ru.apteka.product_card.presentation.product_card_manufacturer_program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Карточка товара, программы производителя".
 */
@HiltViewModel
class ProductCardManufacturerProgramViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    private val _products = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     * Возвращает список продуктов.
     */
    val products: LiveData<List<ProductCardModel>> = _products


    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { getProductsFake() },
                onSuccess = { products ->
                    _products.postValue(
                        products.map { product ->
                            ProductCardModel(
                                product = product
                            ).apply {
                                favorite = ProductFavoriteModel(
                                    favoriteService = favoriteService,
                                    isFavorite = product.isFavorite,
                                )
                                basket = BasketModel(
                                    basketService = basketService,
                                    countInBasket = product.countInBasket
                                )
                            }
                        }
                    )
                },
                onLoading = {
                    //_isProductsLoading.postValue(it)
                }
            )
        }
    }

}