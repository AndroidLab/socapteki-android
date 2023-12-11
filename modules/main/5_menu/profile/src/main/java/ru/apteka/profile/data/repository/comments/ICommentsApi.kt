package ru.apteka.profile.data.repository.comments


import retrofit2.http.POST
import ru.apteka.profile.data.models.CommentModel


/**
 * Описывает методы комментариев и отзывов.
 */
interface ICommentsApi {

    /**
     * Получает отзывы.
     */
    @POST("comments/get")
    suspend fun getComments(): List<ru.apteka.profile.data.models.CommentModel>

}