package ru.apteka.home.data.repository.other


import retrofit2.http.POST
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.PromotionModel


/**
 * Описывает методы остальное.
 */
interface IOtherApi {

    /**
     * Получает список.
     */
    @POST("other/get")
    suspend fun getOther(): List<OtherModel>

}