package ru.apteka.components.data.repository.orders

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.DiscountModel
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.models.ProductModel
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

/**
 * Представляет репозиторий заказоы.
 * @param ordersApi Advert api.
 */
@ViewModelScoped
class OrdersRepository @Inject constructor(
    private val ordersApi: IOrdersApi
) {

    /**
     * Получает заказы.
     */
    suspend fun getOrders(): List<ru.apteka.components.data.models.OrderModel> {
        delay(1500)
        return listOf(
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.NEW,
                date = 1697711146
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.NEW,
                date = 1697624746
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.IN_PROCESSING,
                date = 1677711395
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.IN_PROCESSING,
                date = 1680389795
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.READY_TO_RECEIVE,
                date = 1682981795
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.READY_TO_RECEIVE,
                date = 1685660195
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.TRANSFERRED_TO_COURIER,
                date = 1688252195
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.TRANSFERRED_TO_COURIER,
                date = 1690930595
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.CANCELED,
                date = 1677711395
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.DELIVERY_POSTPONED,
                date = 1680389795
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.DELIVERY_POSTPONED,
                date = 1682981795
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.BOOKING_EXTENDED,
                date = 1685660195
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.BOOKING_EXTENDED,
                date = 1688252195
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.RECEIVED,
                date = 1690930595
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.RECEIVED,
                date = 1690930595
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.CANCELED,
                date = 1690930595
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.CANCELED,
                date = 1690930595
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.AWAITING_PAYMENT,
                date = 1690930595
            ),
            ru.apteka.components.data.models.OrderModel(
                number = Random.nextInt(99999),
                status = OrderStatus.AWAITING_PAYMENT,
                date = 1690930595
            ),
        )
    }

    /**
     * Получает детали заказа.
     */
    suspend fun getOrderDetails(order: ru.apteka.components.data.models.OrderModel): ru.apteka.components.data.models.OrderDetailsModel {
        delay(1500)
        return ru.apteka.components.data.models.OrderDetailsModel(
            number = order.number,
            status = order.status,
            date = order.date,
            methodDelivery = "Доставка",
            address = "2-я Владимирская ул., 29A, Москва",
            totalProducts = 3,
            bonusCount = "+120",
            paymentMethod = "Онлайн карта",
            totalCost = "4 569,90 ₽",
            products = listOf(
                ProductModel(
                    id = UUID.randomUUID(),
                    image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                    isFavorite = false,
                    price = "от 2 200 ₽",
                    desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                    rating = "",
                    comments = 0,
                    discount = DiscountModel(
                        oldPrice = "от 5 200 ₽",
                        percent = "-30%"
                    )
                ),
                ProductModel(
                    id = UUID.randomUUID(),
                    image = "https://social-apteka.ru/upload/ammina.optimizer/jpg-webp/q80/upload/resize_cache/iblock/26a/4t2rwziwqy0985ppytp7tlwl473rihud/150_150_0/f2848cc6f2c04f92cd3876228dbdf81f.webp",
                    isFavorite = false,
                    price = "от 2 200 ₽",
                    desc = "Диклофенак-акос гель для наружного применения 5% 50 г",
                    rating = "",
                    comments = 0,
                    discount = DiscountModel(
                        oldPrice = "от 5 200 ₽",
                        percent = "-30%"
                    )
                ),
            )
        )
    }


    /**
     * Ищет заказы по номеру.
     */
    suspend fun findOrdersByNumber(number: Int): List<ru.apteka.components.data.models.OrderModel> {
        delay(1500)
        return listOf(
            ru.apteka.components.data.models.OrderModel(
                number = 123,
                status = OrderStatus.TRANSFERRED_TO_COURIER,
                date = 1697711146
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 1234,
                status = OrderStatus.CANCELED,
                date = 1697624746
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 12345,
                status = OrderStatus.CANCELED,
                date = 1677711395
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 123456,
                status = OrderStatus.READY_TO_RECEIVE,
                date = 1680389795
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 1234567,
                status = OrderStatus.TRANSFERRED_TO_COURIER,
                date = 1682981795
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 12345678,
                status = OrderStatus.DELIVERY_POSTPONED,
                date = 1685660195
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 123456789,
                status = OrderStatus.BOOKING_EXTENDED,
                date = 1688252195
            ),
            ru.apteka.components.data.models.OrderModel(
                number = 1234567890,
                status = OrderStatus.RECEIVED,
                date = 1690930595
            )
        ).filter { it.number.toString().contains(number.toString()) }
    }

}