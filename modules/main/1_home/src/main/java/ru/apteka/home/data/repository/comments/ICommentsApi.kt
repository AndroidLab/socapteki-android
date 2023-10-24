package ru.apteka.home.data.repository.comments


import retrofit2.http.POST
import ru.apteka.home.data.models.BonusModel
import ru.apteka.home.data.models.CommentModel
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.PromotionModel


/**
 * Описывает методы комментариев и отзывов.
 */
interface ICommentsApi {

    /**
     * Получает отзывы.
     */
    @POST("comments/get")
    suspend fun getComments(): List<CommentModel>

}