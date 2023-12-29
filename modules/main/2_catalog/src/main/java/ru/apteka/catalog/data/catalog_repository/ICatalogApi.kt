package ru.apteka.catalog.data.catalog_repository


import androidx.annotation.Keep
import retrofit2.http.POST
import ru.apteka.components.data.models.ProductModel
import ru.apteka.catalog.data.models.IFilter


/**
 * Описывает методы каталога.
 */
@Keep
interface ICatalogApi {

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