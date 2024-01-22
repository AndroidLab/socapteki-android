package ru.apteka.favorites.data.repository.favorites


import retrofit2.http.POST

/**
 * Описывает методы избранного.
 */
interface IFavoritesApi {

    /**
     * Получает список избранных.
     */
    @POST("favorites/get")
    suspend fun getFavorites(): List<Unit>

}