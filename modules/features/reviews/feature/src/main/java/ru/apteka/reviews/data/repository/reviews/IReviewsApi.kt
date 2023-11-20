package ru.apteka.reviews.data.repository.reviews


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IReviewsApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}