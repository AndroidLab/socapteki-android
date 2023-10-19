package ru.apteka.components.data.repository


import retrofit2.http.POST
import ru.apteka.components.data.models.OrderDetailsModel
import ru.apteka.components.data.models.OrderModel

/**
 * Описывает методы заказов.
 */
interface IOrdersApi {

    /**
     * Получает список заказов.
     */
    @POST("orders/get")
    suspend fun getOrders(): List<ru.apteka.components.data.models.OrderModel>


    /**
     * Получает детали заказа.
     */
    @POST("orders/getOrderDetails")
    suspend fun getOrderDetails(number: String): ru.apteka.components.data.models.OrderDetailsModel

}