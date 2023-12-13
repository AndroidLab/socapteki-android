package ru.apteka.symptoms_diseases.data.repository.symptoms_diseases


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