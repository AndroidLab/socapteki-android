package ru.apteka.social.data.repository

import kotlinx.coroutines.delay
import ru.apteka.social.data.repository.remote.api.ILoginApi
import ru.apteka.social.domain.login.entity.CheckCodeResult
import ru.apteka.social.domain.login.entity.NewCodeResult
import ru.apteka.social.domain.login.entity.SendPhoneResult
import ru.apteka.social.domain.login.usecase.NewCodeUseCase
import javax.inject.Inject


/**
 * Представляет репозиторий авторизации.
 * @param loginApi Login api.
 */
class LoginRepository @Inject constructor(
    private val loginApi: ILoginApi
) {
    /**
     * Отправляет номер.
     * @param phoneNumber
     */
    suspend fun sendNumber(phoneNumber: String): SendPhoneResult {
        delay(1500)
        return SendPhoneResult(true)
    }

    /**
     * Получить новый код.
     */
    suspend fun getNewCode(phoneNumber: String): NewCodeResult {
        delay(1500)
        return NewCodeResult(true)
    }

    /**
     * Провверяет код.
     */
    suspend fun checkCode(code: String): CheckCodeResult {
        delay(1500)
        return CheckCodeResult(true)
    }



}