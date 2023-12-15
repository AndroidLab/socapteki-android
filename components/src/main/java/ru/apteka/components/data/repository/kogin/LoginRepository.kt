package ru.apteka.components.data.repository.kogin

import kotlinx.coroutines.delay
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.models.SubscriptionsModel
import ru.apteka.components.data.services.account.AccountsPreferences
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет репозиторий авторизации.
 * @param loginApi Login api.
 */
@Singleton
class LoginRepository @Inject constructor(
    private val loginApi: ILoginApi,
    private val accountsPreferences: AccountsPreferences
) {

    /**
     * Запрашивает новый код.
     */
    suspend fun requestCode(phoneNumber: String): Boolean {
        delay(1500)
        return true
    }

    /**
     * Провверяет код.
     */
    suspend fun checkCode(code: String): Boolean {
        delay(1500)
        return true
    }


    private var _personalData: PersonalData = PersonalData(
        fio = null,
        date = null,
        phone = accountsPreferences.account?.phoneNumber,
        userMail = null,
        sex = null,
        isReceiveReceipts = false
    )

    /**
     * Получает личные данные.
     */
    suspend fun getPersonalData(): PersonalData {
        delay(1500)
        return _personalData
    }

    /**
     * Сохраняет личные данные.
     */
    suspend fun savePersonalData(personalData: PersonalData) {
        delay(1500)
        _personalData = personalData
    }

    suspend fun savePersonalDataFio(fio: String) {
        delay(1500)
        _personalData = _personalData.copy(fio = fio)
    }

    suspend fun savePersonalDataPhone(phone: String): Boolean {
        delay(1500)
        _personalData = _personalData.copy(phone = phone)
        return true
    }

    suspend fun savePersonalDataSex(sex: Int) {
        delay(1500)
        _personalData = _personalData.copy(sex = sex)
    }

    suspend fun savePersonalDataMail(mail: String): Boolean {
        delay(1500)
        _personalData = _personalData.copy(userMail = PersonalData.UserMail(mail, true))
        return true
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