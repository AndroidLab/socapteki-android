package ru.apteka.catalog.data.catalog_repository


import retrofit2.http.POST
import ru.apteka.components.data.models.ProductModel
import ru.apteka.catalog.data.models.IFilter


/**
 * Описывает методы каталога.
 */
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