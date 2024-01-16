package ru.apteka.basket.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.basket.data.models.AlwaysUsefulFilterModel
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.getProductsFake
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
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращат модель фильтра для 'Всегда пригодится'.
     */
    val alwaysUsefulFilter = AlwaysUsefulFilterModel(
        _items = listOf(
            AlwaysUsefulFilterModel.Item(
                status = "Все"
            ),
            AlwaysUsefulFilterModel.Item(
                status = "Годен менее 3 месяцев"
            ),
            AlwaysUsefulFilterModel.Item(
                status = "Популярное 1"
            ),
            AlwaysUsefulFilterModel.Item(
                status = "Популярное 2"
            ),
            AlwaysUsefulFilterModel.Item(
                status = "Популярное 3"
            ),
        )
    ).apply {
        items[0].isItemSelected.value = true
    }

    private val _alwaysUsefulProducts = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     * Возвращает товары 'Всегда пригодится'
     */
    val alwaysUsefulProducts: LiveData<List<ProductCardModel>> = _alwaysUsefulProducts

    private val _alwaysUsefulIsLoading = MutableLiveData(false)

    /**
     * Ворзвращает флаг загрузки товаров.
     */
    val alwaysUsefulIsLoading: LiveData<Boolean> = _alwaysUsefulIsLoading

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { getProductsFake() },
                onSuccess = { products ->
                    _alwaysUsefulProducts.postValue(
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
                isLoading = _alwaysUsefulIsLoading
            )
        }
    }

}