package ru.apteka.listing.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.models.SortModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IBottomSheetService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.listing.data.models.FilterType
import ru.apteka.listing.data.models.IFilter
import java.util.Random
import javax.inject.Inject


/**
 * Представляет модель представления "Листинг".
 */
@HiltViewModel
class ListingViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val productsRepository: ProductsRepository,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    val bottomSheetService: IBottomSheetService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    //TODO Возможно еще понадобится.
    /*private val _alphabet = listOf(
        "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "К", "Л", "М",
        "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"
    )*/

    /**
     * Возвращает алфовит.
     */
    //TODO Возможно еще понадобится.
    /*val alphabet = MutableLiveData(
        AlphabetModel(
            _items = listOf(
                AlphabetModel.Item(
                    name = "Все"
                )
            ) + _alphabet.map {
                AlphabetModel.Item(
                    name = it
                )
            }
        ) {
            Log.d("myL", "Выбрали букву " + it.name)
        }
    )*/

    private val productsCount = MutableLiveData(55)

    private fun getCountProduction() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    delay(1500)
                    Random().nextInt(99)
                },
                onSuccess = {
                    productsCount.postValue(it)
                },
                onFailure = {
                    productsCount.postValue(0)
                },
                onLoading = {
                    if (it) {
                        productsCount.postValue(-1)
                    }
                }
            )
        }
    }

    /**
     * Модель для сортировки.
     */
    val sortModel = SortModel()

    /**
     * Возвращает список продуктов.
     */
    val products = ScopedLiveData<ViewModel, List<ProductCardModel>>(emptyList())

    /**
     * Возвращает флаг загрузки продуктов.
     */
    val isProductsLoading = ScopedLiveData(false)

    /**
     * Получает продукты.
     */
    fun getCatalogProducts() {
        products.setValue(emptyList<ProductCardModel>())
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { getProductsFake().subList(0, 6) },
                onSuccess = {
                    filters.postValue(filtersFake)
                    products.postValue(
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
                    isProductsLoading.postValue(it)
                }
            )
        }
    }


    private val filtersFake = listOf(
        IFilter.FilterPriceModel(
            type = FilterType.PRICE,
            title = "Цена",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                //Log.d("myL", "1 getCountProduction price")
                getCountProduction()
            },
            minPrice = 100,
            maxPrice = 200,
        ),
        IFilter.FilterReleaseFormModel(
            type = FilterType.RELEASE_FORM,
            title = "Форма выпуска",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                //Log.d("myL", "1 getCountProduction ReleaseForm")
                getCountProduction()
            },
            items = listOf(
                IFilter.FilterReleaseFormModel.ReleaseFormModel(
                    title = "Жидкое",
                    desk = "Капли, настойка, настои, сироп, суспензия, эмульсия"
                ),
                IFilter.FilterReleaseFormModel.ReleaseFormModel(
                    title = "Твердые",
                    desk = "Капсула, таблетка, порошки, гранулы, драже, карамель, карандаш"
                ),
                IFilter.FilterReleaseFormModel.ReleaseFormModel(
                    title = "Мягкие",
                    desk = "Крем, мазь, гель, суппозитории, пастаsit amet, consectetur"
                ),
                IFilter.FilterReleaseFormModel.ReleaseFormModel(
                    title = "Аэрозоли",
                ),
            )
        ),
        IFilter.FilterManufacturerModel(
            type = FilterType.MANUFACTURER,
            title = "Производитель",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                getCountProduction()
            },
            items = listOf(
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Johnson & Johnson",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Pfizer",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Sinopharm",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Roche Holding",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Johnson & Johnson",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Еще производитель 1",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Еще производитель 2",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Еще производитель 3",
                ),
                IFilter.FilterManufacturerModel.ManufacturerModel(
                    title = "Еще производитель 4",
                ),
            )
        ),
        IFilter.FilterDiscountsModel(
            type = FilterType.DISCOUNTS,
            title = "Акции",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                getCountProduction()
            },
            items = listOf(
                IFilter.FilterDiscountsModel.DiscountItemModel(
                    title = "Акции",
                ),
                IFilter.FilterDiscountsModel.DiscountItemModel(
                    title = "Истекающий срок годности",
                ),
                IFilter.FilterDiscountsModel.DiscountItemModel(
                    title = "Скидки",
                )
            )
        ),
        IFilter.FilterNosologyModel(
            type = FilterType.NOSOLOGY,
            title = "Нозология",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                //getCountProduction(it.productsCount)
            },
            items = listOf(
                IFilter.FilterNosologyModel.NosologyModel(
                    title = "Воспаления и инфекции"
                ),
                IFilter.FilterNosologyModel.NosologyModel(
                    title = "Другие"
                ),
                IFilter.FilterNosologyModel.NosologyModel(
                    title = "Обезболивающие"
                ),
                IFilter.FilterNosologyModel.NosologyModel(
                    title = "Перхоть"
                ),
                IFilter.FilterNosologyModel.NosologyModel(
                    title = "Опрелость"
                )
            ),
        ),
        IFilter.FilterBrandModel(
            type = FilterType.BRAND,
            title = "Бренды",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                getCountProduction()
            },
            items = listOf(
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Ауробин",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Декарис",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Плавикс",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Сеафор",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Еще бренд 1",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Еще бренд 2",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Еще бренд 3",
                ),
                IFilter.FilterBrandModel.BrandItemModel(
                    title = "Еще бренд 4",
                ),
            )
        ),
        IFilter.FilterCountryModel(
            type = FilterType.COUNTRY,
            title = "Страны",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                getCountProduction()
            },
            items = listOf(
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Венгрия",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Германия",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Россия",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Франция",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Еще страна 1",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Еще страна 2",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Еще страна 3",
                ),
                IFilter.FilterCountryModel.CountryItemModel(
                    title = "Еще страна 4",
                ),
            )
        ),
        IFilter.FilterActiveSubstanceModel(
            type = FilterType.ACTIVE_SUBSTANCE,
            title = "Действующее вещество",
            foundProducts = products,
            productsCount = productsCount,
            onChanged = {
                getCountProduction()
            },
            items = listOf(
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Декспантенол, преднизолон, лидокаин"
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Доксазозин"
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Итраконазол"
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Клопидогрел"
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Левамизол"
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Метформин"
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Еще вещество 1",
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Еще вещество 2",
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Еще вещество 3",
                ),
                IFilter.FilterActiveSubstanceModel.ActiveSubstanceItemModel(
                    title = "Еще вещество 4",
                ),
            )
        )
    )

    /**
     * Возвращает список доступных фильтров.
     */
    val filters = ScopedLiveData<ViewModel, List<IFilter>>(emptyList())

    /**
     *
     */
    val filterAll: LiveData<IFilter.FilterAllModel?> = filters.map {
        if (it.isEmpty()) {
            null
        } else {
            IFilter.FilterAllModel(
                filters = it,
                foundProducts = products,
                productsCount = productsCount,
                onChanged = {
                    Log.d("myL", "2 getCountProduction")
                    //getCountProduction()
                },
            )
        }
    }

    /**
     * Возвращает товары 'С этим товаром покупают'.
     */
    val withProducts = ScopedLiveData(emptyList<ProductCardModel>())

    /**
     * Возвращает флаг загрузки продуктов дня.
     */
    val withProductsIsLoading = ScopedLiveData(false)

    /**
     * Возвращает товары 'С этим товаром покупают'.
     */
    val watchedRecently = ScopedLiveData(emptyList<ProductCardModel>())

    /**
     * Возвращает флаг загрузки продуктов дня.
     */
    val watchedRecentlyIsLoading = ScopedLiveData(false)

    init {
        viewModelScope.launchIO {
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { products ->
                        mainThread {
                            withProducts.postValue(
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
                        }
                    },
                    onLoading = {
                        withProductsIsLoading.postValue(it)
                    }
                )
            }
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { products ->
                        mainThread {
                            watchedRecently.postValue(
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
                        }
                    },
                    onLoading = {
                        watchedRecentlyIsLoading.postValue(it)
                    }
                )
            }
        }
    }

}