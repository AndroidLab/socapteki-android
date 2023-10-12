package ru.apteka.favorites.presentation.favorites.data.repository.favorites


import retrofit2.http.POST
import ru.apteka.favorites.presentation.favorites.data.models.FavoriteModel

/**
 * Описывает методы избранного.
 */
interface IFavoritesApi {

    /**
     * Получает список избранных.
     */
    @POST("favorites/get")
    suspend fun getFavorites(): List<FavoriteModel>

}