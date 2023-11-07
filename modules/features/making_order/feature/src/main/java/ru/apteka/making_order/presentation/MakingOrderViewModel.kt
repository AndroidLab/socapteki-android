package ru.apteka.making_order.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.Label
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_PRODUCT
import java.util.UUID
import javax.inject.Inject


/**
 * Представляет модель представления "Оформление заказа".
 */
@HiltViewModel
class MakingOrderViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val userPreferences: UserPreferences,
    private val savedStateHandle: SavedStateHandle,
    navigationManager: NavigationManager,
    messageNoticeService: MessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    //
    val additionalProducts = listOf(
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            isFavorite = false,
            price = "от 18 913 ₽",
            desc = "Диклофенак-акос гель для наружного применения 5% 50 г адлфвоа фдлоа жофд аафвлождало фоа жофр ажшор жфщшаро щшгрофашщрофщжгаро фш а",
            rating = "4.7",
            comments = 123,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            additionalDesc = "Без рецепта",
            labels = listOf(
                Label.PRODUCT_DAY
            )
        ),
        ProductModel(
            id = UUID.randomUUID(),
            image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
            isFavorite = false,
            price = "от 16 913 ₽",
            desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
            rating = "4.7",
            comments = 321,
            discount = DiscountModel(
                "22 131 ₽",
                "30%"
            ),
            labels = listOf(
                Label.PRODUCT_DAY,
                Label.ADVERT
            )
        ),
    )

    /**
     * Возвращает или устанавливает список продукции для заказа.
     */
    val productsForOrder = MutableLiveData(savedStateHandle.get<Array<ProductModel>>(MAKING_ORDER_ARGUMENT_PRODUCT)!!.toList())

    /**
     * Возврпащает выбранный город.
     */
    val selectedCity = userPreferences.cityFlow.asLiveData()

    init {

    }

}