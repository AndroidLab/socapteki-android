package ru.apteka.notifications.data.repository.notifications


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface INotificationsApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}