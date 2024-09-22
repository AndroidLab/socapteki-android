package ru.apteka.home.presentation.bonus_ticket_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.models.SortModel
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IBottomSheetService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.BonusModel
import ru.apteka.home.data.models.BonusStubModel
import ru.apteka.home.presentation.home.HomeViewModel
import javax.inject.Inject

/**
 * Представляет модель представления "Детали тикета".
 */
@HiltViewModel
class BonusTicketDetailsViewModel @Inject constructor(
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    val bottomSheetService: IBottomSheetService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Модель для сортировки.
     */
    val sortModel = SortModel()

    /**
     * Возвращает список продуктов.
     */
    val products = ScopedLiveData<ViewModel, List<ProductCardModel>>(emptyList())

    init {
        viewModelScope.launchIO {
            products.postValue(
                getProductsFake().subList(0, 6).map { product ->
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
        }
    }
}
