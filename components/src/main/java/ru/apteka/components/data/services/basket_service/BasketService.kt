package ru.apteka.components.data.services.basket_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.R
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.utils.contains
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис корзины.
 */
@Singleton
class BasketService @Inject constructor(
    private val messageService: IMessageService,
) {
    private val _basketProducts = MutableLiveData<List<ProductModel>>(emptyList())

    /**
     * Возвращает список продукции.
     */
    val basketProducts: LiveData<List<ProductModel>> = _basketProducts

    private val _totalCount = MutableLiveData(0)

    /**
     * Возвращает кол-во продукции в корзине.
     */
    val totalCount: LiveData<Int> = _totalCount

    /**
     * Обновляет кол-во товаров в корзине.
     */
    fun updateTotalCount() {
        _totalCount.value = _basketProducts.value!!.sumOf { it.countInBasketLiveData.value!! }
    }

    /**
     * Добавляет продукцию в корзину.
     */
    fun addProduct(productCard: ProductModel) {
        _basketProducts.value = _basketProducts.value!!.plus(productCard)
        updateTotalCount()
    }

    /**
     * Удаляет продукцию из корзины.
     */
    fun removeProduct(product: ProductModel) {
        _basketProducts.value = _basketProducts.value!!.minus(product.apply { setCount(0) })
        updateTotalCount()
    }

    /**
     * Очищает корзину.
     */
    fun removeAll() {
        messageService.showCommonDialog(
            dialogModel = DialogModel(
                message = MessageModel(
                    message = R.string.dialog_remove_product_all_desk
                ),
                buttonCancel = DialogButtonModel(
                    text = R.string.cancel,
                    textColor = R.color.light_black,
                    borderColor = R.color.grey,
                ),
                buttonConfirm = DialogButtonModel(
                    text = R.string.remove,
                    backgroundColor = R.color.red
                ) {
                    _basketProducts.value = emptyList()
                    updateTotalCount()
                    /*_basketProducts.value!!.forEach {
                        it.onProductRemove()
                    }*/
                }
            )
        )
    }

    /**
     * Возвращает флаг содержания продукции в корзине.
     */
    fun isContainsInBasket(uuid: UUID) =
        _basketProducts.value!!.contains { it.id == uuid }
}