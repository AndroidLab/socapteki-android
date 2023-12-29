package ru.apteka.choosing_city.data.repository.new_repository


import androidx.annotation.Keep
import retrofit2.http.POST
import ru.apteka.components.data.services.user.models.CityModel

/**
 * Описывает методы городов.
 */
@Keep
interface ICitiesApi {

    /**
     * Получает список городов.
     */
    @POST("cities/get")
    suspend fun getCities(query: String): List<CityModel>

}