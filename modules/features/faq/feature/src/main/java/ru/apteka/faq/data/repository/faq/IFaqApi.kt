package ru.apteka.feedback.data.repository.feedback


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IFaqApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}