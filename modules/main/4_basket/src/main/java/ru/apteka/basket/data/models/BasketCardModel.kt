package ru.apteka.basket.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.R
import ru.apteka.components.data.models.FavoriteModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.utils.DownTimer
import java.text.DecimalFormat


/**
 * Представляет модель для карточки продукта в корзине.
 * @param product Продукт.
 */
data class BasketCardModel(
    val product: ProductModel,
) {
    /**
     * Возвращает или устанавливает модель обработки избранного.
     */
    lateinit var favorite: FavoriteModel

    /**
     * Возвращает или устанавливает модель обработки корзины.
     */
    lateinit var basket: BasketModel

    lateinit var onShowRemoveDialog: () -> Unit

    private val downTimer = DownTimer(
        10, {
            _productLeftTime.value = it.let {
                val minutes = it / 60
                val seconds = it % 60
                val format = DecimalFormat("00")
                "${format.format(minutes)}:${format.format(seconds)}"
            }
        }, {
            _productLeftTime.value = null
            basket.clear(product)
        }
    )

    private val _productLeftTime = MutableLiveData<String?>(null)

    /**
     * Возвращает время до удаления продукта.
     */
    val productLeftTime: LiveData<String?> = _productLeftTime

    /**
     * Обработчик удаления продукта.
     */
    fun removeProduct() {
        downTimer.startTimer()
    }

    /**
     * Обработчик востановления продукта.
     */
    fun restoreProduct() {
        downTimer.cancelTimer()
        _productLeftTime.value = null
    }

}
