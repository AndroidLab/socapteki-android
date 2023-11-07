package ru.apteka.making_order.data.repository.new_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IMakingOrderApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}