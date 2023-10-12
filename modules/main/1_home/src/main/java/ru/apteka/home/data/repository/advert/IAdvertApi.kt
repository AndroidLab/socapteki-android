package ru.apteka.home.data.repository.advert


import retrofit2.http.POST
import ru.apteka.home.data.models.AdvertModel

/**
 * Описывает методы рекламы.
 */
interface IAdvertApi {

    /**
     * Получает список рекламы.
     */
    @POST("favorites/get")
    suspend fun getFavorites(): List<AdvertModel>

}