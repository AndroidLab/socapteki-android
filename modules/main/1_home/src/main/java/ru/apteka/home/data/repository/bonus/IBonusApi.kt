package ru.apteka.home.data.repository.bonus


import retrofit2.http.POST
import ru.apteka.home.data.models.BonusModel
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.PromotionModel


/**
 * Описывает методы бонусов.
 */
interface IBonusApi {

    /**
     * Получает историю бонусов.
     */
    @POST("bonus/get")
    suspend fun getBonusHistory(): List<BonusModel>

}