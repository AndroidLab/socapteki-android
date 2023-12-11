package ru.apteka.about_company.data.repository.about_company


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IApi {

    /**
     * Получает список избранных.
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}