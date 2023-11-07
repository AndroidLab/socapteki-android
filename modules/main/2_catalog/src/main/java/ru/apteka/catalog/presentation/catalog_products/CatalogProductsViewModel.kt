package ru.apteka.catalog.presentation.catalog_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.catalog.data.catalog_repository.CatalogRepository
import ru.apteka.catalog.data.models.IFilter
import ru.apteka.catalog.data.models.SearchHistoryHeaderModel
import ru.apteka.catalog.data.models.SearchResultHeaderModel
import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.data.models.SortModel
import ru.apteka.catalog.data.services.SearchProductPreferences
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.barcode_scan.BarCodeScanService
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.bottom_sheet_service.IBottomSheetService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.debounce
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Товары каталога".
 */
@HiltViewModel
class CatalogProductsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val catalogRepository: CatalogRepository,
    private val productsRepository: ProductsRepository,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    private val savedStateHandle: SavedStateHandle,
    val searchProductPreferences: SearchProductPreferences,
    val bottomSheetService: IBottomSheetService,
    val barCodeScanService: BarCodeScanService,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    /**
     * Возвращает выбранный пункт каталога.
     */
    val catalogItemName = savedStateHandle.get<String>("catalogItemName")!!

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
                filters = it
            )
        }
    }

    /**
     *
     */
    val sortModel = SortModel()


    private val _products = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val products: LiveData<List<ProductCardModel>> = _products

    private val _isProductsLoading = MutableLiveData(false)

    /**
     *
     */
    val isProductsLoading: LiveData<Boolean> = _isProductsLoading

    /**
     * Получает продукты.
     */
    fun getCatalogProducts() {
        _products.value = emptyList()
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { productsRepository.getProductions() },
                onSuccess = { products ->
                    _filters.postValue(catalogRepository.getFilters())
                    _products.postValue(
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
                onLoading = {
                    _isProductsLoading.postValue(it)
                }
            )
        }
    }


    private val _productsWithProductBuy = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsWithProductBuy: LiveData<List<ProductCardModel>> = _productsWithProductBuy

    private val _productsWithProductBuyIsLoading = MutableLiveData(false)

    /**
     *
     */
    val productsWithProductBuyIsLoading: LiveData<Boolean> = _productsWithProductBuyIsLoading

    /**
     * Получает продукты с которыми покупают.
     */
    fun getProductsWithProductBuy() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { productsRepository.getProductions() },
                onSuccess = { products ->
                    _productsWithProductBuy.postValue(
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
                onLoading = {
                    _productsWithProductBuyIsLoading.postValue(it)
                }
            )
        }
    }


    private val _productsRecentlyWatched = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val productsRecentlyWatched: LiveData<List<ProductCardModel>> = _productsRecentlyWatched

    private val _productsRecentlyWatchedIsLoading = MutableLiveData(false)

    /**
     *
     */
    val productsRecentlyWatchedIsLoading: LiveData<Boolean> = _productsRecentlyWatchedIsLoading

    /**
     * Получает продукты вы недавно смотрели.
     */
    fun getProductsRecentlyWatched() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { productsRepository.getProductions() },
                onSuccess = { products ->
                    _productsRecentlyWatched.postValue(
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
                onLoading = {
                    _productsRecentlyWatchedIsLoading.postValue(it)
                }
            )
        }
    }


    /**
     * Показать больше.
     */
    fun showMore() {

    }


    private val _foundResults = MutableLiveData<List<Any>>(emptyList())

    /**
     * Возвращает найденные результаты.
     */
    val foundResults: LiveData<List<Any>> = _foundResults

    /**
     * Возвращает или устанавливает введенный текст для поиска.
     */
    val searchText = MutableLiveData("")

    /**
     * Возвращает или устанавливает текст для поиска.
     */
    var textQuery = ""


    /**
     * Возвращает текст поиска заказа.
     */
    val onSearchTextChange = viewModelScope.debounce<String>(1000L) { value ->
        if (value.isNotBlank()) {
            if (value != textQuery) {
                searchProducts(value)
            }
        } else {
            clearData()
        }
        textQuery = value
    }

    private val _isSearchProductsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг поиска продукции.
     */
    val isSearchProductsLoading = _isSearchProductsLoading


    /**
     * Возвращает флаг показа экрана поиска.
     */
    val isSearchShow =
        MediatorLiveData(catalogItemName == CatalogProductsFragment.SEARCH_MODE).apply {
            addSource(isSearchProductsLoading) {
                if (it) value = true
            }
            addSource(isProductsLoading) {
                if (it) value = false
            }
        }

    private suspend fun searchProducts(value: String) {
        val historyRequests = searchProductPreferences.getHistoryRequest(value).let {
            buildList {
                if (it.isNotEmpty()) {
                    add(
                        SearchHistoryHeaderModel {
                            searchProductPreferences.clear()
                            _foundResults.value = _foundResults.value!!.filter {
                                it !is SearchHistoryHeaderModel && it is SearchResultHeaderModel || (it is SearchResultModel && it.type != SearchResultModel.SearchResultType.HISTORY)
                            }
                        }
                    )
                    addAll(
                        it.map {
                            SearchResultModel(
                                type = SearchResultModel.SearchResultType.HISTORY,
                                text = it
                            )
                        }
                    )
                }
            }
        }
        _foundResults.postValue(historyRequests)

        requestHandler.handleApiRequest(
            onRequest = {
                catalogRepository.searchResult(value)
            },
            onSuccess = { searchResults ->
                _foundResults.postValue(
                    buildList {
                        addAll(historyRequests)
                        if (searchResults.isNotEmpty()) {
                            add(SearchResultHeaderModel())
                            addAll(
                                searchResults
                            )
                        }
                    }
                )
            },
            isLoading = _isSearchProductsLoading
        )
    }

    /**
     * Очищает данные.
     */
    private fun clearData() {
        _foundResults.postValue(emptyList())
    }

}