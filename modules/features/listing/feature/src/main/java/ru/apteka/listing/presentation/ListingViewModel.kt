package ru.apteka.listing.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IBottomSheetService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.listing.data.models.AlphabetModel
import ru.apteka.listing.data.models.FilterType
import ru.apteka.listing.data.models.IFilter
import ru.apteka.listing.data.models.SortModel
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


    private val _alphabet = listOf(
        "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "К", "Л", "М",
        "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"
    )

    /**
     * Возвращает алфовит.
     */
    val alphabet = MutableLiveData(
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
    )

    private fun getCountProduction(productsCount: MutableLiveData<Int>) {
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

    private val _products = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     * Возвращает список продуктов.
     */
    val products: LiveData<List<ProductCardModel>> = _products

    private val _isProductsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки продуктов.
     */
    val isProductsLoading: LiveData<Boolean> = _isProductsLoading

    /**
     * Получает продукты.
     */
    fun getCatalogProducts() {
        _products.value = emptyList()
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { getProductsFake() },
                onSuccess = { products ->
                    _filters.postValue(filtersFake)
                    _products.postValue(
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
                onLoading = {
                    _isProductsLoading.postValue(it)
                }
            )
        }
    }


    private val filtersFake = listOf(
        IFilter.FilterPriceModel(
            type = FilterType.PRICE,
            title = "Цена",
            foundProducts = products,
            onChanged = {
                getCountProduction(it.productsCount)
            },
            minPrice = 100,
            maxPrice = 200,
        ),
        IFilter.FilterReleaseFormModel(
            type = FilterType.RELEASE_FORM,
            title = "Форма выпуска",
            foundProducts = products,
            onChanged = {
                getCountProduction(it.productsCount)
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
            onChanged = {
                getCountProduction(it.productsCount)
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
            onChanged = {
                getCountProduction(it.productsCount)
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
            onChanged = {
                getCountProduction(it.productsCount)
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
            onChanged = {
                getCountProduction(it.productsCount)
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
            onChanged = {
                getCountProduction(it.productsCount)
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

    private val _filters = MutableLiveData<List<IFilter>>(emptyList())

    /**
     * Возвращает список доступных фильтров.
     */
    val filters: MutableLiveData<List<IFilter>> = _filters

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
                onChanged = {
                    getCountProduction(it.productsCount)
                },
            )
        }
    }

}