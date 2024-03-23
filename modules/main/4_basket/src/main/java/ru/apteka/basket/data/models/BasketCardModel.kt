package ru.apteka.basket.data.models

import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.basket_service.models.BasketModel
import ru.apteka.components.data.utils.DownTimer
import ru.apteka.components.data.utils.ScopedLiveData
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
    lateinit var favorite: ProductFavoriteModel

    /**
     * Возвращает или устанавливает модель обработки корзины.
     */
    lateinit var basket: BasketModel

    lateinit var onShowRemoveDialog: () -> Unit

    private val downTimer = DownTimer(
        10, {
            productLeftTime.setValue(it.let {
                val minutes = it / 60
                val seconds = it % 60
                val format = DecimalFormat("00")
                "${format.format(minutes)}:${format.format(seconds)}"
            })
        }, {
            productLeftTime.setValue(null)
            basket.clear(product)
        }
    )

    /**
     * Возвращает время до удаления продукта.
     */
    val productLeftTime = ScopedLiveData<BasketCardModel, String?>(null)

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
        productLeftTime.setValue(null)
    }

}
