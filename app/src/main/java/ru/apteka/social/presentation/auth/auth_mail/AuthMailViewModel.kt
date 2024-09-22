package ru.apteka.social.presentation.auth.auth_mail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.R
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.models.SexModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.validateEmail
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Авторизация майл".
 */
@HiltViewModel
class AuthMailViewModel @Inject constructor(
    private val accountsPreferences: AccountsPreferences,
    private val loginRepository: LoginRepository,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает или устанавливает ФИО.
     */
    val fio = MutableLiveData("")

    /**
     * Возвращает или устанавливает дату рождения.
     */
    val birthday = MutableLiveData("")

    /**
     * Возвращает или устанавливает почту.
     */
    val email = MutableLiveData("")

    /**
     * Возвращает ошибку валидации.
     */
    val isEmailValid = email.map {
        if (validateEmail(email.value!!)) null else R.string.email_not_valid
    }

    val sexModel = SexModel(
        _items = listOf(SexModel.Item(1), SexModel.Item(2))
    ) {

    }

    /**
     * Возвращает или устанавливает флаг отправки электронных чеков.
     */
    val isReceiveReceipts = MutableLiveData(false)

    /**
     * Возвращает флаг доступности отправки сообщения.
     */
    val isFieldsFilled = MediatorLiveData<Boolean>().apply {
        fun checkFieldsFilled() {
            value =
                isLoading.value == false && fio.value!!.isNotEmpty() && email.value!!.isNotEmpty() && sexModel.selectedItem.value != null
        }

        addSource(fio) {
            checkFieldsFilled()
        }

        addSource(email) {
            checkFieldsFilled()
        }

        addSource(sexModel.selectedItem) {
            checkFieldsFilled()
        }

        addSource(isLoading) {
            checkFieldsFilled()
        }
    }

    /**
     * Возвращает или устанавливает код подтверждения.
     */
    val code = MutableLiveData("")

    /**
     * Возвращает или устанавливает флаг отправки кода.
     */
    val isCodeSend = ScopedLiveData(false)

    /**
     * Возвращает или устанавливает флаг верификации пин кода.
     */
    val isMailVerified = ScopedLiveData(false)

    /**
     * Возвращает или устанавливает флаг ошибки ввода пин кода.
     * TODO Пока не используется
     */
    val isCodeConfirmError = ScopedLiveData(false)

    /**
     * Получить код на почту.
     */
    fun receiveCode() {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            isCodeSend.postValue(true)
            isLoading.postValue(false)
        }
    }

    /**
     * Верефицировать почту.
     */
    fun verifiedMail() {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            isMailVerified.postValue(true)
            isLoading.postValue(false)
        }
    }

    /**
     * Сохраняет персональные данные.
     */
    fun save(onSuccess: () -> Unit) {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            loginRepository.savePersonalData(
                PersonalData(
                    fio = fio.value!!,
                    date = birthday.value!!,
                    phone = accountsPreferences.account?.phoneNumber,
                    userMail = PersonalData.UserMail(
                        mail = email.value!!,
                        isVerified = isMailVerified.value!!
                    ),
                    sex = sexModel.selectedItem.value!!.sex,
                    isReceiveReceipts = isReceiveReceipts.value!!
                )
            )
            mainThread {
                onSuccess()
            }
            isLoading.postValue(false)
        }
    }
}