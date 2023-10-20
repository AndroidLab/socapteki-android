package ru.apteka.catalog.presentation.catalog_products

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.catalog.R
import ru.apteka.catalog.data.catalog_repository.CatalogRepository
import ru.apteka.catalog.data.models.IFilter
import ru.apteka.catalog.data.models.SortModel
import ru.apteka.components.data.models.ItemCounterModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.navigation_manager.NavigationManager
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
    val navigationManager: NavigationManager
) : BaseViewModel() {

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

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { catalogRepository.getFilters() },
                onSuccess = { filters ->
                    _filters.postValue(filters)
                },
                isLoading = _isLoading
            )
        }
        getProducts()
    }

    private val _products = MutableLiveData<List<ProductCardModel>>(emptyList())

    /**
     *
     */
    val products: LiveData<List<ProductCardModel>> = _products

    private val _productsIsLoading = MutableLiveData(false)

    /**
     *
     */
    val productsIsLoading: LiveData<Boolean> = _productsIsLoading

    /**
     * Получает продукты.
     */
    fun getProducts() {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { productsRepository.getProductions() },
                onSuccess = { products ->
                    _products.postValue(
                        products.map { product ->
                            ProductCardModel(
                                product = product,
                                onFavoriteClick = {

                                },
                                onByeOneClick = {

                                },
                                itemCounter = ItemCounterModel(
                                    onMinus = {

                                    },
                                    onPlus = {

                                    }
                                )
                            )
                        }
                    )
                },
                onLoading = {
                    _productsIsLoading
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
                                product = product,
                                onFavoriteClick = {

                                },
                                onByeOneClick = {

                                },
                                itemCounter = ItemCounterModel(
                                    onMinus = {

                                    },
                                    onPlus = {

                                    }
                                )
                            )
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
                                product = product,
                                onFavoriteClick = {

                                },
                                onByeOneClick = {

                                },
                                itemCounter = ItemCounterModel(
                                    onMinus = {

                                    },
                                    onPlus = {

                                    }
                                )
                            )
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


    /**
     * Показать назначение лекарств.
     */
    val onShowPrescribingMedications: (view: View) -> Unit = {
        navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
            CatalogProductsFragmentDirections.toCatalogProductRecommendationFragment(
                R.string.catalog_product_recommendation_prescribing_medications,
                R.string.catalog_product_recommendation_prescribing_medications_desc
            )
        )
    }

    /**
     * Показать формы выпуска.
     */
    val onShowReleaseForm: (view: View) -> Unit = {
        navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
            CatalogProductsFragmentDirections.toCatalogProductRecommendationFragment(
                R.string.catalog_product_recommendation_release_form,
                R.string.catalog_product_recommendation_release_form_desk
            )
        )
    }

    /**
     * Показать как выбрать и заказать лекарство.
     */
    val onShowToChooseAndOrder: (view: View) -> Unit = {
        navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
            CatalogProductsFragmentDirections.toCatalogProductRecommendationFragment(
                R.string.catalog_product_recommendation_how_to_choose,
                R.string.catalog_product_recommendation_how_to_choose_desk
            )
        )
    }


}