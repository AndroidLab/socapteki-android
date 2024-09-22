package ru.apteka.listing.data.repository.listing


import androidx.annotation.Keep
import retrofit2.http.POST
import ru.apteka.components.data.models.ProductModel
import ru.apteka.listing.data.models.IFilter


/**
 * Описывает методы каталога.
 */
@Keep
interface IListingApi {

    /**
     * Получает список товаров.
     */
    @POST("catalog/getProducts")
    suspend fun getProducts(type: String, filter: String): List<ProductModel>

    /**
     * Получает список доступных фильтров.
     */
    @POST("catalog/getFilters")
    suspend fun getFilters(): List<IFilter>

}