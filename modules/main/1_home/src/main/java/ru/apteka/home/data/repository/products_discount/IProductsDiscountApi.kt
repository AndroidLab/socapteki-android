package ru.apteka.home.data.repository.products_discount


import retrofit2.http.POST
import ru.apteka.components.data.models.ProductModel


/**
 * Описывает методы продуктов дня.
 */
interface IProductsDiscountApi {

    /**
     * Получает список продуктов дня.
     */
    @POST("products_day/get")
    suspend fun getProductsDay(): List<ProductModel>

}