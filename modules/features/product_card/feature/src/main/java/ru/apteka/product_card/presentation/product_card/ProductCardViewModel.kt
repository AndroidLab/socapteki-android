package ru.apteka.product_card.presentation.product_card

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.getProductsFake
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.product_card.data.model.CommentModel
import ru.apteka.product_card.data.model.InstructionModel
import ru.apteka.product_card.data.repository.product_card.ProductCardRepository
import javax.inject.Inject


/**
 * Представляет модель представления "Карточка товара".
 */
@HiltViewModel
class ProductCardViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val productsRepository: ProductsRepository,
    private val productCardRepository: ProductCardRepository,
    private val basketService: BasketService,
    private val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает продукцию.
     */
    val product = MutableLiveData<ProductModel?>(null)

    val favorite = product.map {
        it?.let { product ->
            ProductFavoriteModel(
                favoriteService = favoriteService,
                isFavorite = product.isFavorite,
            )
        }
    }

    val basket = product.map {
        it?.let { product ->
            BasketModel(
                basketService = basketService,
                countInBasket = product.countInBasket
            )
        }
    }

    /**
     * Возвращает ссписок аннологичных товаров.
     */
    val analoguesProducts = ScopedLiveData<ViewModel, List<ProductCardModel>>()

    /**
     * Возвращает флаг загрузки аннологичных товаров.
     */
    val isAnaloguesProductsLoading = ScopedLiveData(false)


    /**
     * Возвращает ссписок с этим товаром покупают.
     */
    val withProducts = ScopedLiveData<ViewModel, List<ProductCardModel>>()

    /**
     * Возвращает флаг загрузки с этим товаром покупают.
     */
    val isWithProductsLoading = ScopedLiveData(false)

    /**
     * Возвращает инструкцию к товару.
     */
    val productInstruction = ScopedLiveData<ViewModel, List<InstructionModel.InstructionItem>>()

    /**
     * Возвращает флаг загрузки инструкции товара.
     */
    val isProductInstructionLoading = ScopedLiveData(false)

    /**
     * Возвращает комментарии к товару.
     */
    val productComments = ScopedLiveData<ViewModel, CommentModel>()

    /**
     * Возвращает флаг загрузки комментариев.
     */
    val isProductCommentsLoading = ScopedLiveData(false)


    init {
        viewModelScope.launchIO {
            /*launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productCardRepository.getProductDetails() },
                    onSuccess = { product ->
                        _product.postValue(product)
                    },
                    isLoading = _isLoading
                )
            }*/

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { products ->
                        analoguesProducts.postValue(
                            products.map { product ->
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
                    },
                    onLoading = {
                        isAnaloguesProductsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { products ->
                        withProducts.postValue(
                            products.map { product ->
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
                    },
                    onLoading = {
                        isWithProductsLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productCardRepository.getInstruction() },
                    onSuccess = { instruction ->
                        productInstruction.postValue(instruction.instructions)
                    },
                    onLoading = {
                        isProductInstructionLoading.postValue(it)
                    }
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productCardRepository.getProductComments() },
                    onSuccess = { comments ->
                        productComments.postValue(comments)
                    },
                    onLoading = {
                        isProductCommentsLoading.postValue(it)
                    }
                )
            }
        }
    }

    /**
     * Загрузить комментарии по популярности.
     */
    fun popularCommentsSort() {

    }

    /**
     * Показать больше комментариев.
     */
    fun showMoreComments() {

    }

}