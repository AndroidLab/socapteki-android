package ru.apteka.home.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.navigation_manager.INavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.AdvertModel
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.ProductCardModel
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
    private val userPreferences: UserPreferences,
    val navigationManager: INavigationManager
) : BaseViewModel() {

    /**
     * Возвращает название выбранного города.
     */
    val selectedCity: LiveData<String?> = userPreferences.cityFlow.asLiveData().map {
        it?.name
    }


    private val _adverts = MutableLiveData<List<AdvertModel>>(emptyList())

    /**
     *
     */
    val adverts: LiveData<List<AdvertModel>> = _adverts


    private val _promotions = MutableLiveData<List<PromotionModel>>(emptyList())

    /**
     *
     */
    val promotions: LiveData<List<PromotionModel>> = _promotions


    private val _productsDay = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsDay: LiveData<List<ProductCardModel>> = _productsDay


    private val _productsDiscount = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsDiscount: LiveData<List<ProductCardModel>> = _productsDiscount


    private val _others = MutableLiveData<List<OtherModel>>(emptyList())

    /**
     *
     */
    val others: LiveData<List<OtherModel>> = _others


    init {
        viewModelScope.launchIO {
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { advertRepository.getAdvert() },
                    onSuccess = { adverts ->
                        _adverts.postValue(adverts)
                    }
                )
            }
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { promotionRepository.getPromotions() },
                    onSuccess = { promotions ->
                        _promotions.postValue(promotions)
                    }
                )
            }
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productsDayRepository.getProductionsDay() },
                    onSuccess = { productsDay ->
                        _productsDay.postValue(
                            productsDay.map { product ->
                                ProductCardModel(
                                    product = product,
                                    onFavoriteClick = {

                                    },
                                    onByeOneClick = {

                                    }
                                )
                            }
                        )
                    }
                )
            }
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productsDiscountRepository.getProductionsDiscount() },
                    onSuccess = { productsDiscount ->
                        _productsDiscount.postValue(
                            productsDiscount.map { product ->
                                ProductCardModel(
                                    product = product,
                                    onFavoriteClick = {

                                    },
                                    onByeOneClick = {

                                    }
                                )
                            }
                        )
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { otherRepository.getOther() },
                    onSuccess = { others ->
                        _others.postValue(others)
                    }
                )
            }
        }
    }


}