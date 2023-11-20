package ru.apteka.faq.data.repository.faq

import kotlinx.coroutines.delay
import ru.apteka.faq.data.model.FaqModel
import ru.apteka.feedback.data.repository.feedback.IFaqApi
import javax.inject.Inject

/**
 * Представляет репозиторий faq.
 * @param faqApi Faq api.
 */
class FaqRepository @Inject constructor(
    private val faqApi: IFaqApi
) {

    /**
     * Получает faq.
     */
    suspend fun getFaq(): List<FaqModel> {
        delay(1500)
        return listOf(
            FaqModel(
                title = "Как оформить заказ?",
                desc = "Вы можете оформить заказ несколькими способами: в приложении, на сайте и по телефону с оператором."
            ),
            FaqModel(
                title = "Где посмотреть статус заказа?",
                desc = "Вы можете оформить заказ несколькими способами: в приложении, на сайте и по телефону с оператором."
            ),
            FaqModel(
                title = "Какая минимальная сумма заказа?",
                desc = "Вы можете оформить заказ несколькими способами: в приложении, на сайте и по телефону с оператором."
            ),
            FaqModel(
                title = "Как отменить заказ?",
                desc = "Вы можете оформить заказ несколькими способами: в приложении, на сайте и по телефону с оператором."
            ),
            FaqModel(
                title = "Почему заказ отменили?",
                desc = "Вы можете оформить заказ несколькими способами: в приложении, на сайте и по телефону с оператором."
            ),

        )
    }


}