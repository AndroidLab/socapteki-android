package ru.apteka.components.data.services.basket_service.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.models.ProductCardModel
import ru.apteka.components.data.utils.DownTimer
import java.text.DecimalFormat


/**
 * Представляет модель продукта в корзине.
 */
data class BasketProductCardModel(
    val productCard: ProductCardModel,
    val onShowRemoveDialog: (BasketProductCardModel) -> Unit
) {
    private val downTimer = DownTimer(
        20, {
            _productLeftTime.value = it.let {
                val minutes = it / 60
                val seconds = it % 60
                val format = DecimalFormat("00")
                "${format.format(minutes)}:${format.format(seconds)}"
            }
        }, {
            _productLeftTime.value = null
            productCard.itemCounter.clear()
        }
    )

    private val _productLeftTime = MutableLiveData<String?>(null)

    /**
     * Возвращает флаг удаления продукта.
     */
    val productLeftTime: LiveData<String?> = _productLeftTime

    /**
     * Обработчик удаления продукта.
     */
    fun onProductRemove() {
        downTimer.start()
    }

    /**
     * Обработчик востановления продукта.
     */
    fun onProductRestore() {
        downTimer.cancel()
        _productLeftTime.value = null
    }
}
