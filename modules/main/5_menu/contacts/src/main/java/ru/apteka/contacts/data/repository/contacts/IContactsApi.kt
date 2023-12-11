package ru.apteka.contacts.data.repository.contacts


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IContactsApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}