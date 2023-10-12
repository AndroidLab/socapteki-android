package ru.apteka.home.data.repository.promotion


import retrofit2.http.POST
import ru.apteka.home.data.models.PromotionModel


/**
 * Описывает методы акций.
 */
interface IPromotionApi {

    /**
     * Получает список акций.
     */
    @POST("promotions/get")
    suspend fun getPromotions(): List<PromotionModel>

}