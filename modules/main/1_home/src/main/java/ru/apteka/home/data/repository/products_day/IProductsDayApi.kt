package ru.apteka.home.data.repository.products_day


import retrofit2.http.POST
import ru.apteka.components.data.models.ProductModel


/**
 * Описывает методы продуктов дня.
 */
interface IProductsDayApi {

    /**
     * Получает список продуктов дня.
     */
    @POST("products_day/get")
    suspend fun getProductsDay(): List<ProductModel>

}