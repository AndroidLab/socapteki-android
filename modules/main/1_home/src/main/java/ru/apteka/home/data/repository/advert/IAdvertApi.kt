package ru.apteka.home.data.repository.advert


import androidx.annotation.Keep
import retrofit2.http.GET
import retrofit2.http.POST
import ru.apteka.home.data.models.AdvertModel

/**
 * Описывает методы рекламы.
 */
@Keep
interface IAdvertApi {

    /**
     * Получает список рекламы.
     */
    @GET("favorites/get")
    suspend fun getFavorites(): List<AdvertModel>

}