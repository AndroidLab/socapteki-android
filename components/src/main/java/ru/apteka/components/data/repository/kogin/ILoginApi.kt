package ru.apteka.components.data.repository.kogin

import androidx.annotation.Keep
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Описывает методы авторизации.
 */
@Keep
interface ILoginApi {

    /**
     * Отправляет код.
     */
    @POST("auth/code")
    suspend fun sendCode(
        @Body loginRequest: String
    ): Boolean
}