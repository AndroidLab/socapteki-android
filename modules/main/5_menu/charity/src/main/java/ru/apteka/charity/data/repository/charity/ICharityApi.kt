package ru.apteka.charity.data.repository.charity


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface ICharityApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}