package ru.apteka.components.data.services.basket_service

import ru.apteka.components.R
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.utils.ScopedLiveData
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

    /**
     * Возвращает список продукции.
     */
    val basketProducts = ScopedLiveData<BasketService, List<ProductModel>>(emptyList())

    /**
     * Возвращает кол-во продукции в корзине.
     */
    val totalCount = ScopedLiveData(0)

    /**
     * Обновляет кол-во товаров в корзине.
     */
    fun updateTotalCount() {
        totalCount.setValue(basketProducts.value!!.sumOf { it.countInBasketLiveData.value!! })
    }

    /**
     * Добавляет продукцию в корзину.
     */
    fun addProduct(productCard: ProductModel) {
        basketProducts.setValue(basketProducts.value!!.plus(productCard))
        updateTotalCount()
    }

    /**
     * Удаляет продукцию из корзины.
     */
    fun removeProduct(product: ProductModel) {
        basketProducts.setValue(basketProducts.value!!.minus(product.apply { setCount(0) }))
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
                    basketProducts.setValue(emptyList<ProductModel>())
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
        basketProducts.value!!.contains { it.id == uuid }
}