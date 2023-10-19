package ru.apteka.new_module.data.repository.partners_repository

import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IPartnersApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}