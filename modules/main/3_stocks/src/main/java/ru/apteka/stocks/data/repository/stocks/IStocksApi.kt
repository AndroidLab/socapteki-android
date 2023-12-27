package ru.apteka.stocks.data.repository.stocks


import retrofit2.http.POST
import ru.apteka.stocks.data.models.StockModel

/**
 * Описывает методы избранного.
 */
interface IStocksApi {

    /**
     * Получает список избранных.
     */
    @POST("favorites/get")
    suspend fun getFavorites(): List<StockModel>

}