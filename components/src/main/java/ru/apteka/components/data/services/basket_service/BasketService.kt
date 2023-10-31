package ru.apteka.components.data.services.basket_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.R
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.basket_service.models.BasketProductCardModel
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.utils.contains
import ru.apteka.components.ui.CommonDialogFragment
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис корзины.
 */
@Singleton
class BasketService @Inject constructor(
    private val messageNoticeService: MessageNoticeService,
) {
    private val _products = MutableLiveData<List<BasketProductCardModel>>(emptyList())

    /**
     * Возвращает список продукции.
     */
    val products: LiveData<List<BasketProductCardModel>> = _products

    /**
     * Возвращает кол-во продукции в корзине.
     */
    val totalCount = MediatorLiveData<Int>()

    /**
     * Добавляет продукцию в корзину.
     */
    fun addProduct(productCard: ProductCardModel) {
        _products.value = _products.value!!.plus(
            BasketProductCardModel(
                productCard
            ) { productCardModel ->
                messageNoticeService.showCommonDialog(
                    dialogModel = DialogModel(
                        message = MessageModel(
                            message = R.string.dialog_remove_product_desk
                        ),
                        buttonCancel = DialogButtonModel(
                            text = R.string.cancel
                        ),
                        buttonConfirm = DialogButtonModel(
                            text = R.string.remove,
                        ) {
                            productCardModel.itemCounter.clear()
                        }
                    )
                )
            }
        )
        totalCount.addSource(productCard.itemCounter.counterLiveData) {
            if (it != 0) {
                totalCount.postValue(getTotalCount())
            }
        }
    }

    /**
     * Удаляет продукцию из корзины.
     */
    fun removeProduct(productCard: ProductCardModel) {
        _products.value = _products.value!!.minus(
            products.value!!.single { it.productCard.product.id == productCard.product.id }
        )
        totalCount.removeSource(productCard.itemCounter.counterLiveData)
        totalCount.postValue(getTotalCount())
    }

    /**
     * Возвращает флаг содержания продукции в корзине.
     */
    fun isContainsInBasket(uuid: UUID) = _products.value!!.contains { it.productCard.product.id == uuid }

    private fun getTotalCount() = _products.value!!.sumOf { it.productCard.itemCounter.counterLiveData.value!! }
}