package ru.apteka.components.data.services.basket_service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.R
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.services.basket_service.models.BasketProductCardModel
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
    private val _basketProducts = MutableLiveData<List<BasketProductCardModel>>(emptyList())

    /**
     * Возвращает список продукции.
     */
    val basketProducts: LiveData<List<BasketProductCardModel>> = _basketProducts

    /**
     * Возвращает кол-во продукции в корзине.
     */
    val totalCount = MediatorLiveData<Int>()

    /**
     * Добавляет продукцию в корзину.
     */
    fun addProduct(productCard: ProductCardModel) {
        _basketProducts.value = _basketProducts.value!!.plus(
            BasketProductCardModel(
                productCard
            ) { basketProductCard ->
                messageService.showCommonDialog(
                    dialogModel = DialogModel(
                        message = MessageModel(
                            message = R.string.dialog_remove_product_desk
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
                            basketProductCard.onProductRemove()
                            //basketProductCard.productCard.itemCounter.clear()
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
        _basketProducts.value = _basketProducts.value!!.minus(
            basketProducts.value!!.single { it.productCard.product.id == productCard.product.id }
        )
        totalCount.removeSource(productCard.itemCounter.counterLiveData)
        totalCount.postValue(getTotalCount())
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
                    _basketProducts.value!!.forEach {
                        it.productCard.itemCounter.clear()
                    }
                }
            )
        )
    }

    /**
     * Возвращает флаг содержания продукции в корзине.
     */
    fun isContainsInBasket(uuid: UUID) =
        _basketProducts.value!!.contains { it.productCard.product.id == uuid }

    private fun getTotalCount() =
        _basketProducts.value!!.sumOf { it.productCard.itemCounter.counterLiveData.value!! }
}