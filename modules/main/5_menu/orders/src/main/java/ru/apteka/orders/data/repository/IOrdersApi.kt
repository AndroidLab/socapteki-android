package ru.apteka.orders.data.repository


import androidx.annotation.Keep
import retrofit2.http.POST
import ru.apteka.components.data.models.OrderModel

/**
 * Описывает методы заказов.
 */
@Keep
interface IOrdersApi {

    /**
     * Получает список заказов.
     */
    @POST("orders/get")
    suspend fun getOrders(): List<OrderModel>


}