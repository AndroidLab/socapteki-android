package ru.apteka.stocks.presentation.stock_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.stocks.data.models.StockModel
import javax.inject.Inject


/**
 * Представляет модель представления "Детали акции".
 */
@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    private val _products = MutableLiveData<List<ProductCardModel>>()

    /**
     * Возвращает продукцию.
     */
    val products: LiveData<List<ProductCardModel>> = _products

    /**
     * Возвращает или устанавливает акцию.
     */
    val stock: MutableLiveData<StockModel> = MutableLiveData(null)

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _products.postValue(
                getProductsFake().map { product ->
                    ProductCardModel(
                        product = product,
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
            _isLoading.postValue(false)
        }
    }


}