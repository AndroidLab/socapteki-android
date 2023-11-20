package ru.apteka.licenses.data.repository.licenses_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface ILicensesApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}