package ru.apteka.our_partners.data.repository.our_partners_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IOurPartnersApi {

    /**
     * Получает список избранных.
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}