package ru.apteka.social.data.repository.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.apteka.social.domain.login.entity.CheckCodeResult
import ru.apteka.social.domain.login.entity.SendPhoneResult

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