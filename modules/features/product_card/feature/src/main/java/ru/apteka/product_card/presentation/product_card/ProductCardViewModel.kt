package ru.apteka.product_card.presentation.product_card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.models.ProductCounterModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.repository.products.ProductsRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
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

    private val _analoguesProducts = MutableLiveData<List<ProductCardModel>>()

    /**
     * Возвращает ссписок аннологичных товаров.
     */
    val analoguesProducts: LiveData<List<ProductCardModel>> = _analoguesProducts

    private val _isAnaloguesProductsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки аннологичных товаров.
     */
    val isAnaloguesProductsLoading: LiveData<Boolean> = _isAnaloguesProductsLoading


    private val _withProductProducts = MutableLiveData<List<ProductCardModel>>()

    /**
     * Возвращает ссписок с этим товаром покупают.
     */
    val withProducts: LiveData<List<ProductCardModel>> = _withProductProducts

    private val _isWithProductsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки с этим товаром покупают.
     */
    val isWithProductsLoading: LiveData<Boolean> = _isWithProductsLoading


    private val _productInstruction = MutableLiveData<List<InstructionModel.InstructionItem>>()

    /**
     * Возвращает инструкцию к товару.
     */
    val productInstruction: LiveData<List<InstructionModel.InstructionItem>> = _productInstruction

    private val _isProductInstructionLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки инструкции товара.
     */
    val isProductInstructionLoading: LiveData<Boolean> = _isProductInstructionLoading


    private val _productComments = MutableLiveData<CommentModel>()

    /**
     * Возвращает комментарии к товару.
     */
    val productComments: LiveData<CommentModel> = _productComments

    private val _isProductCommentsLoading = MutableLiveData(false)

    /**
     * Возвращает флаг загрузки комментариев.
     */
    val isProductCommentsLoading: LiveData<Boolean> = _isProductCommentsLoading


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
                        _analoguesProducts.postValue(
                            products.map { product ->
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
                    },
                    isLoading = _isAnaloguesProductsLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { getProductsFake() },
                    onSuccess = { products ->
                        _withProductProducts.postValue(
                            products.map { product ->
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
                    },
                    isLoading = _isWithProductsLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productCardRepository.getInstruction() },
                    onSuccess = { instruction ->
                        _productInstruction.postValue(instruction.instructions)
                    },
                    isLoading = _isProductInstructionLoading
                )
            }

            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { productCardRepository.getProductComments() },
                    onSuccess = { comments ->
                        _productComments.postValue(comments)
                    },
                    isLoading = _isProductCommentsLoading
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