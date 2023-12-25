package ru.apteka.home.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.AdvertModel
import ru.apteka.home.data.models.OrderCardModel
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.PromotionModel
import ru.apteka.home.data.repository.advert.AdvertRepository
import ru.apteka.home.data.repository.other.OtherRepository
import ru.apteka.home.data.repository.products_day.ProductsDayRepository
import ru.apteka.home.data.repository.products_discount.ProductsDiscountRepository
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
    private val productsDayRepository: ProductsDayRepository,
    private val productsDiscountRepository: ProductsDiscountRepository,
    private val otherRepository: OtherRepository,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    private val fakeOrdersCard = buildList<OrderCardModel> {
        repeat(3) {
            add(
                OrderCardModel(
                    type = "Самовывоз",
                    status = "Принят",
                    number = "№789290",
                    desc = "1 товар - 495 ₽ | Создан: 23.11.2023 10:32",
                )
            )
        }
    }

    private val _ordersCard = MutableLiveData<List<OrderCardModel>>(emptyList())

    /**
     * Возвращает список заказоы.
     */
    val ordersCard: LiveData<List<OrderCardModel>> = _ordersCard

    private val _ordersCardIsLoading = MutableLiveData(false)

    /**
     * Возвращает фллг загрузки заказов.
     */
    val  ordersCardIsLoading: LiveData<Boolean> = _ordersCardIsLoading


    private val _adverts = MutableLiveData<List<AdvertModel>>(emptyList())

    /**
     *
     */
    val adverts: LiveData<List<AdvertModel>> = _adverts

    private val _advertsIsLoading = MutableLiveData(false)

    /**
     *
     */
    val advertsIsLoading: LiveData<Boolean> = _advertsIsLoading


    private val _promotions = MutableLiveData<List<PromotionModel>>(emptyList())

    /**
     *
     */
    val promotions: LiveData<List<PromotionModel>> = _promotions

    private val _promotionsIsLoading = MutableLiveData(false)

    /**
     *
     */
    val promotionsIsLoading: LiveData<Boolean> = _promotionsIsLoading


    private val _productsDay = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsDay: LiveData<List<ProductCardModel>> = _productsDay

    private val _productsDayIsLoading = MutableLiveData(false)

    /**
     *
     */
    val productsDayIsLoading: LiveData<Boolean> = _productsDayIsLoading


    private val _productsDiscount = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsDiscount: LiveData<List<ProductCardModel>> = _productsDiscount

    private val _productsDiscountIsLoading = MutableLiveData(false)

    /**
     *
     */
    val productsDiscountIsLoading: LiveData<Boolean> = _productsDiscountIsLoading


    private val _others = MutableLiveData<List<OtherModel>>(emptyList())

    /**
     *
     */
    val others: LiveData<List<OtherModel>> = _others

    private val _othersIsLoading = MutableLiveData(false)

    /**
     *
     */
    val othersIsLoading: LiveData<Boolean> = _othersIsLoading


    init {
        viewModelScope.launchIO {
            launchIO {
                _ordersCardIsLoading.postValue(true)
                delay(1000)
                _ordersCard.postValue(fakeOrdersCard)
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
                    onRequest = { promotionRepository.getPromotions() },
                    onSuccess = { promotions ->
                        _promotions.postValue(promotions)
                    },
                    isLoading = _promotionsIsLoading
                )
            }
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productsDayRepository.getProductionsDay() },
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
                    onRequest = { productsDiscountRepository.getProductionsDiscount() },
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
                        _others.postValue(others)
                    },
                    isLoading = _othersIsLoading
                )
            }
        }

    }


}