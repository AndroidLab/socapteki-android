package ru.apteka.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.OrderDeliveryMethod
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderPayStatus
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.getProductsFake2
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.AdvertModel
import ru.apteka.home.data.models.BonusModel
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.PromotionFilterModel
import ru.apteka.home.data.models.PromotionModel
import ru.apteka.home.data.repository.advert.AdvertRepository
import ru.apteka.home.data.repository.other.OtherRepository
import ru.apteka.home.data.repository.promotion.PromotionRepository
import javax.inject.Inject

/**
 * Представляет модель представления "Главная".
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val advertRepository: AdvertRepository,
    private val promotionRepository: PromotionRepository,
    private val otherRepository: OtherRepository,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    private val accountsPreferences: AccountsPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает аккаунт.
     */
    val account = accountsPreferences.accountFlow.asLiveData()

    /**
     * Получает заказы.
     */
    suspend fun getOrdersFake(): List<OrderModel> {
        return listOf(
            OrderModel(
                number = 123,
                status = OrderStatus.NEW,
                date = 1697711146,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            ),
            OrderModel(
                number = 12345,
                status = OrderStatus.NEW,
                date = 1697624746,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            ),
            OrderModel(
                number = 123456,
                status = OrderStatus.IN_PROCESSING,
                date = 1677711395,
                payStatus = OrderPayStatus.SUCCESS,
                sum = "4 559,90 ₽",
                deliveryMethod = OrderDeliveryMethod.DELIVERY,
                deliveryAddress = "2-я Владимирская ул. 29А, Москва",
                paymentMethod = "Онлайн",
                products = getProductsFake2(),
            )
        )
    }

    /**
     * Возвращает список заказоы.
     */
    val ordersCard = ScopedLiveData<ViewModel, List<OrderModel>>(emptyList())

    /**
     * Возвращает фллг загрузки заказов.
     */
    val ordersCardIsLoading = ScopedLiveData(false)

    /**
     * Возвращает список рекламных блоков.
     */
    val adverts = ScopedLiveData<ViewModel, List<AdvertModel>>(emptyList())

    /**
     * Возвращает флаг загрузки рекламных блоков.
     */
    val advertsIsLoading = ScopedLiveData(false)

    /**
     * Возвращат модель фильтра для акций.
     */
    val promotionFilter = PromotionFilterModel(
        _items = listOf(
            PromotionFilterModel.Item(
                status = "Все"
            ),
            PromotionFilterModel.Item(
                status = "Лекарства"
            ),
            PromotionFilterModel.Item(
                status = "Витамины и бады"
            ),
            PromotionFilterModel.Item(
                status = "Активный"
            ),
        )
    ) {
        // ordersPreferences.orderFilter = it.status
    }.apply {
        items[0].isItemSelected.value = true
    }

    /**
     * Возвращает акции.
     */
    val promotions = ScopedLiveData(emptyList<PromotionModel>())

    /**
     * Возвращает флаг загрузки акций.
     */
    val promotionsIsLoading = ScopedLiveData(false)

    /**
     * Возвращает список продуктов дня.
     */
    val productsDay = ScopedLiveData(emptyList<ProductCardModel>())

    /**
     * Возвращает флаг загрузки продуктов дня.
     */
    val productsDayIsLoading = ScopedLiveData(false)

    /**
     * Возращает продукты со скидкой.
     */
    val productsDiscount = ScopedLiveData(emptyList<ProductCardModel>())

    /**
     * Возвращает флаг загрузки продуктов со скидкой.
     */
    val productsDiscountIsLoading = ScopedLiveData(false)

    /**
     * Возвращает список категорий.
     */
    val categories = ScopedLiveData(emptyList<OtherModel>())

    /**
     * Возвращает флаг загрузки категорий.
     */
    val categoriesIsLoading = ScopedLiveData(false)

    private val fakeBonuses = listOf(
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = 320,
            desc = "Интернет заказ"
        ),
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = 320,
            desc = "Интернет заказ"
        ),
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = -320,
            desc = "Покупка в розницу"
        ),
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = 320,
            desc = "Интернет заказ"
        ),
        BonusModel(
            title = "Списание",
            date = 1697711146,
            value = -320,
            desc = "Покупка в розницу"
        ),
    )

    /**
     *
     */
    val bonuses = ScopedLiveData(emptyList<BonusModel>())

    /**
     *
     */
    val bonusesLoading = ScopedLiveData(true)

    init {
        viewModelScopeLaunch {
            launchIO {
                ordersCardIsLoading.postValue(true)
                delay(800)
                ordersCard.postValue(getOrdersFake())
                ordersCardIsLoading.postValue(false)
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { advertRepository.getAdvert() },
                    onSuccess = {
                        adverts.postValue(it)
                    },
                    onLoading = {
                        advertsIsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = {
                        mainThread {
                            productsDay.postValue(
                                it.map { product ->
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
                        }
                    },
                    onLoading = {
                        productsDayIsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { promotionRepository.getPromotions() },
                    onSuccess = {
                        promotions.postValue(it)
                    },
                    onLoading = {
                        promotionsIsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = {
                        productsDiscount.postValue(
                            it.map { product ->
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
                        productsDiscountIsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { otherRepository.getOther() },
                    onSuccess = { others ->
                        categories.postValue(others)
                    },
                    onLoading = {
                        categoriesIsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                viewModelScope.launchIO {
                    bonusesLoading.postValue(true)
                    delay(750)
                    bonuses.postValue(fakeBonuses)
                    bonusesLoading.postValue(false)
                }
            }
        }
    }
}
