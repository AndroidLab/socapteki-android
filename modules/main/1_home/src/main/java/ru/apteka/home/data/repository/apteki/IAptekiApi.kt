package ru.apteka.home.data.repository.apteki


import retrofit2.http.POST
import ru.apteka.home.data.models.AptekaModel
import ru.apteka.home.data.models.OtherModel
import ru.apteka.home.data.models.PromotionModel


/**
 * Описывает методы аптек.
 */
interface IAptekiApi {

    /**
     * Получает список аптек.
     */
    @POST("apteki/get")
    suspend fun getApteki(): List<AptekaModel>

}