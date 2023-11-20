package ru.apteka.feedback.data.repository.feedback


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface ICooperationApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}