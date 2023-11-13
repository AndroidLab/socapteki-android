package ru.apteka.home.data.repository.apteki


import retrofit2.http.POST
import ru.apteka.home.data.models.PharmacyModel


/**
 * Описывает методы аптек.
 */
interface IAptekiApi {

    /**
     * Получает список аптек.
     */
    @POST("apteki/get")
    suspend fun getApteki(): List<PharmacyModel>

}