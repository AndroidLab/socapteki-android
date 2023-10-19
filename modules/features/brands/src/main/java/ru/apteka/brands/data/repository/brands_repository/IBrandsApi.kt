package ru.apteka.brands.data.repository.brands_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IBrandsApi {

    /**
     * Получает список избранных.
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}