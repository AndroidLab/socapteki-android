package ru.apteka.components.data.repository.kogin

import retrofit2.http.Body
import retrofit2.http.POST
import ru.apteka.components.data.models.CheckCodeResult
import ru.apteka.components.data.models.SendPhoneResult

/**
 * Описывает методы авторизации.
 */
interface ILoginApi {

    /**
     * Отправляет номер телефона.
     * @param loginRequest
     */
    @POST("auth/phone")
    suspend fun sendPhone(
        @Body loginRequest: String
    ): SendPhoneResult

    /**
     * Отправляет код.
     */
    @POST("auth/code")
    suspend fun sendCode(
        @Body loginRequest: String
    ): CheckCodeResult
}