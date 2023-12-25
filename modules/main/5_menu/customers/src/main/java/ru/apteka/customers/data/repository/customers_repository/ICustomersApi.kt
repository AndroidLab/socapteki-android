package ru.apteka.customers.data.repository.customers_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface ICustomersApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}