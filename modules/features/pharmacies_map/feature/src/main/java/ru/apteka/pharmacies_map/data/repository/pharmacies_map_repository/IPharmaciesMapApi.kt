package ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository


import androidx.annotation.Keep
import retrofit2.http.POST


/**
 * Описывает методы аптек.
 */
@Keep
interface IPharmaciesMapApi {

    /**
     * Получает список аптек.
     */
    @POST("apteki/get")
    suspend fun getApteki(): List<Unit>

}