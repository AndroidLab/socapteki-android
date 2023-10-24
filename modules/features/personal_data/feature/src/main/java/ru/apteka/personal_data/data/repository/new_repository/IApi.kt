package ru.apteka.new_module.data.repository.new_repository


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