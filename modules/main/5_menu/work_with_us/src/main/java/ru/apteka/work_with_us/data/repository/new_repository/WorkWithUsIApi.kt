package ru.apteka.work_with_us.data.repository.new_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface WorkWithUsIApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}