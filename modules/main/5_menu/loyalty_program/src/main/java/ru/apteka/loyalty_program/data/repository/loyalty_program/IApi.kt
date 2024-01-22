package ru.apteka.loyalty_program.data.repository.loyalty_program


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}