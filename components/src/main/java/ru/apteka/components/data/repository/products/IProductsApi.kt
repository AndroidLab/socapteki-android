package ru.apteka.components.data.repository.products


import retrofit2.http.POST
import ru.apteka.components.data.models.OrderDetailsModel
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.ProductModel

/**
 * Описывает методы продуктов.
 */
interface IProductsApi {

    /**
     * Получает список продуктов.
     */
    @POST("products/get")
    suspend fun getProducts(): List<ProductModel>

}