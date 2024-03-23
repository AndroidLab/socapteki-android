package ru.apteka.stocks.presentation.stock_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
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

    /**
     * Возвращает продукцию.
     */
    val products = ScopedLiveData<ViewModel, List<ProductCardModel>>(emptyList())

    /**
     * Возвращает или устанавливает акцию.
     */
    val stock: MutableLiveData<StockModel> = MutableLiveData(null)

    init {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            products.postValue(
                getProductsFake().map { product ->
                    ProductCardModel(
                        product = product,
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
            isLoading.postValue(false)
        }
    }


}