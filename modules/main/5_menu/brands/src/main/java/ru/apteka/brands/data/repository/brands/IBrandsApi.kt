package ru.apteka.brands.data.repository.brands


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IBrandsApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}