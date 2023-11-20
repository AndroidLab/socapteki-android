package ru.apteka.product_card.data.repository.product_card


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IProductCardApi {

    /**
     * Получает детали продукта.
     */
    @POST("product/get")
    suspend fun get(): List<Unit>

}