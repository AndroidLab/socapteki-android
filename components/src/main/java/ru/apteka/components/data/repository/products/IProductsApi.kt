package ru.apteka.components.data.repository.products


import androidx.annotation.Keep
import retrofit2.http.POST
import ru.apteka.components.data.models.ProductModel

/**
 * Описывает методы продуктов.
 */
@Keep
interface IProductsApi {

    /**
     * Получает список продуктов.
     */
    @POST("products/get")
    suspend fun getProducts(): List<ProductModel>

}