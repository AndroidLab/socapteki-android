package ru.apteka.components.data.repository.kogin

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Описывает методы авторизации.
 */
interface ILoginApi {

    /**
     * Отправляет код.
     */
    @POST("auth/code")
    suspend fun sendCode(
        @Body loginRequest: String
    ): Boolean
}