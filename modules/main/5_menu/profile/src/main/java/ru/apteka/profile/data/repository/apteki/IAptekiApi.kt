package ru.apteka.profile.data.repository.apteki


import androidx.annotation.Keep
import retrofit2.http.POST
import ru.apteka.profile.data.models.PharmacyModel


/**
 * Описывает методы аптек.
 */
@Keep
interface IAptekiApi {

    /**
     * Получает список аптек.
     */
    @POST("apteki/get")
    suspend fun getApteki(): List<PharmacyModel>

}