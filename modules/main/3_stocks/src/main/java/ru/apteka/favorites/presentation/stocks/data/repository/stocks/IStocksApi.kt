package ru.apteka.favorites.presentation.stocks.data.repository.stocks


import retrofit2.http.POST
import ru.apteka.favorites.presentation.stocks.data.models.FavoriteModel

/**
 * Описывает методы избранного.
 */
interface IStocksApi {

    /**
     * Получает список избранных.
     */
    @POST("favorites/get")
    suspend fun getFavorites(): List<FavoriteModel>

}