package ru.apteka.home.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.OrderDeliveryMethod
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.OrderPayStatus
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
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
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
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

    private val _ordersCard = MutableLiveData<List<OrderModel>>(emptyList())

    /**
     * Возвращает список заказоы.
     */
    val ordersCard: LiveData<List<OrderModel>> = _ordersCard

    private val _ordersCardIsLoading = MutableLiveData(false)

    /**
     * Возвращает фллг загрузки заказов.
     */
    val ordersCardIsLoading: LiveData<Boolean> = _ordersCardIsLoading


    private val _adverts = MutableLiveData<List<AdvertModel>>(emptyList())

    /**
     * Возвращает список рекламных блоков.
     */
    val adverts: LiveData<List<AdvertModel>> = _adverts

    private val _advertsIsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки рекламных блоков.
     */
    val advertsIsLoading: LiveData<Boolean> = _advertsIsLoading


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
        //ordersPreferences.orderFilter = it.status
    }.apply {
        items[0].isItemSelected.value = true
    }

    private val _promotions = MutableLiveData<List<PromotionModel>>(emptyList())

    /**
     * Возвращает акции.
     */
    val promotions: LiveData<List<PromotionModel>> = _promotions

    private val _promotionsIsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки акций.
     */
    val promotionsIsLoading: LiveData<Boolean> = _promotionsIsLoading


    private val _productsDay = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     * Возвращает список продуктов дня.
     */
    val productsDay: LiveData<List<ProductCardModel>> = _productsDay

    private val _productsDayIsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки продуктов дня.
     */
    val productsDayIsLoading: LiveData<Boolean> = _productsDayIsLoading


    private val _productsDiscount = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     * Возращает продукты со скидкой.
     */
    val productsDiscount: LiveData<List<ProductCardModel>> = _productsDiscount

    private val _productsDiscountIsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки продуктов со скидкой.
     */
    val productsDiscountIsLoading: LiveData<Boolean> = _productsDiscountIsLoading


    private val _categories = MutableLiveData<List<OtherModel>>(emptyList())

    /**
     * Возвращает список категорий.
     */
    val categories: LiveData<List<OtherModel>> = _categories

    private val _categoriesIsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки категорий.
     */
    val categoriesIsLoading: LiveData<Boolean> = _categoriesIsLoading

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

    private val _bonuses = MutableLiveData<List<BonusModel>>(emptyList())

    /**
     *
     */
    val bonuses: LiveData<List<BonusModel>> = _bonuses

    private val _bonusesLoading = MutableLiveData(true)

    /**
     *
     */
    val bonusesLoading: LiveData<Boolean> = _bonusesLoading

    init {
        viewModelScope.launchIO {
            launchIO {
                _ordersCardIsLoading.postValue(true)
                delay(800)
                _ordersCard.postValue(getOrdersFake()!!)
                _ordersCardIsLoading.postValue(false)
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { advertRepository.getAdvert() },
                    onSuccess = { adverts ->
                        _adverts.postValue(adverts)
                    },
                    isLoading = _advertsIsLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { productsDay ->
                        mainThread {
                            _productsDay.postValue(
                                productsDay.map { product ->
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
                        }
                    },
                    isLoading = _productsDayIsLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { promotionRepository.getPromotions() },
                    onSuccess = { promotions ->
                        _promotions.postValue(promotions)
                    },
                    isLoading = _promotionsIsLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { productsDiscount ->
                        _productsDiscount.postValue(
                            productsDiscount.map { product ->
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
                    isLoading = _productsDiscountIsLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { otherRepository.getOther() },
                    onSuccess = { others ->
                        _categories.postValue(others)
                    },
                    isLoading = _categoriesIsLoading
                )
            }

            launchIO {
                viewModelScope.launchIO {
                    _bonusesLoading.postValue(true)
                    delay(750)
                    _bonuses.postValue(fakeBonuses)
                    _bonusesLoading.postValue(false)
                }
            }
        }
    }

}