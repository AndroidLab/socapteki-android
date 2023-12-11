package ru.apteka.profile.data.repository.bonus


import retrofit2.http.POST
import ru.apteka.profile.data.models.BonusModel


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