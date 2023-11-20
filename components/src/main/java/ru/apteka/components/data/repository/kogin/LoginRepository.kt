package ru.apteka.components.data.repository.kogin

import kotlinx.coroutines.delay
import ru.apteka.components.data.models.CheckCodeResult
import ru.apteka.components.data.models.NewCodeResult
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.models.SendPhoneResult
import ru.apteka.components.data.models.SubscriptionsModel
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет репозиторий авторизации.
 * @param loginApi Login api.
 */
@Singleton
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


    private var _personalData: PersonalData? = null

    /**
     * Получает личные данные.
     */
    suspend fun getPersonalData(): PersonalData? {
        delay(2500)
        return _personalData
    }

    /**
     * Сохраняет личные данные.
     */
    suspend fun savePersonalData(personalData: PersonalData) {
        delay(1500)
        _personalData = personalData
    }


    private var _subscriptions: SubscriptionsModel = SubscriptionsModel(false, false)

    /**
     * Возвращает подписки.
     */
    suspend fun getSubscriptions(): SubscriptionsModel {
        delay(1500)
        return _subscriptions
    }

    /**
     * Сохраняет подписки.
     */
    suspend fun saveSubscriptions(subscriptions: SubscriptionsModel) {
        delay(1500)
        _subscriptions = subscriptions
    }
}