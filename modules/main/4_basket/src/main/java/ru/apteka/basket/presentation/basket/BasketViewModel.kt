package ru.apteka.basket.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.basket.data.models.AlwaysUsefulFilterModel
import ru.apteka.basket.data.models.BasketCardModel
import ru.apteka.components.R
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
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
    private val favoriteService: FavoriteService,
    val basketService: BasketService,
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

    /**
     * Возвращает продукты в корзине.
     */
    val productsInBasket = basketService.basketProducts.map {
        it.map { product ->
            BasketCardModel(
                product = product
            ).apply {
                favorite = FavoriteModel(
                    favoriteService = favoriteService,
                    isFavorite = favoriteService.isContainsInFavorite(product.id),
                )
                basket = BasketModel(
                    basketService = basketService,
                    countInBasket = product.countInBasket
                )
                onShowRemoveDialog = {
                    messageService.showCommonDialog(
                        dialogModel = DialogModel(
                            message = MessageModel(
                                message = R.string.dialog_remove_product_desk
                            ),
                            buttonCancel = DialogButtonModel(
                                text = R.string.cancel,
                                textColor = R.color.light_black,
                                borderColor = R.color.grey,
                            ),
                            buttonConfirm = DialogButtonModel(
                                text = R.string.remove,
                                backgroundColor = R.color.red
                            ) {
                                removeProduct()
                            }
                        )
                    )
                }
            }
        }
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
                                basket = BasketModel(
                                    basketService = basketService,
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