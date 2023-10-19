package ru.apteka.new_module.data.repository.new_repository


import retrofit2.http.POST
import ru.apteka.components.data.services.user.models.CityModel

/**
 * Описывает методы городов.
 */
interface ICitiesApi {

    /**
     * Получает список городов.
     */
    @POST("cities/get")
    suspend fun getCities(query: String): List<CityModel>

}