package ru.apteka.referral_program.data.repository.referral_program


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