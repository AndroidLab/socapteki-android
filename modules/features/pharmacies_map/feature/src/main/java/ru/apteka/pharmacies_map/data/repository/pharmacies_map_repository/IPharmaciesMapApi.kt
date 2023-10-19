package ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository


import retrofit2.http.POST


/**
 * Описывает методы .
 */
interface IPharmaciesMapApi {

    /**
     * Получает список .
     */
    @POST("/get")
    suspend fun get(): List<Unit>

}