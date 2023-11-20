package ru.apteka.work_with_us.presentation.work_with_us_questionnaire

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.components.data.utils.validateEmail
import ru.apteka.components.ui.BaseViewModel
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Представляет модель представления "Работа у нас".
 */
@HiltViewModel
class WorkWithUsQuestionnaireViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    private val accountsPreferences: AccountsPreferences,
    private val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращает или устанавливает город.
     */
    val city = MutableLiveData("")

    /**
     * Возвращает или устанавливает ФИО.
     */
    val fio = MutableLiveData("")

    /**
     * Возвращает или устанавливает дату рождения.
     */
    val birthday = MutableLiveData("")


    /**
     * Устанавливает или возвращает номер.
     */
    val phone = MutableLiveData("")

    /**
     * Возвращает номер телефона без маски.
     */
    private fun getPhoneRaw() = phone.value!!
        .replace("+7", "")
        .replace("(", "")
        .replace(")", "")
        .replace("-", "")
        .replace(" ", "")


    /**
     * Устанавливает или возвращает емайл.
     */
    val email = MutableLiveData("")

    /**
     * Возвращает ошибку валидации или верификации.
     */
    val isEmailValid = email.map {
        if (validateEmail(email.value!!)) null else ru.apteka.components.R.string.email_not_valid
    }


    /**
     * Возвращает или устанавливает вакансию.
     */
    val jobOpening = MutableLiveData("")

    /**
     * Возвращает или устанавливает дополнительную информацию.
     */
    val additionalInfo = MutableLiveData("")


    /**
     * Возвращает или устанавливает согласие на об. пер. данных.
     */
    val isPersonalDataChecked = MutableLiveData(false)

    /**
     * Возвращает или устанавливает флаг доступности отправки анкеты.
     */
    val isSendAccess = MediatorLiveData<Boolean>().apply {
        fun checkChange() {
            value = !isLoading.value!! && city.value!!.isNotEmpty() && validateEmail(email.value!!)
                    && fio.value!!.isNotEmpty() && birthday.value!!.isNotEmpty() && getPhoneRaw().isNotEmpty()
                    && email.value!!.isNotEmpty() && isPersonalDataChecked.value!!
        }

        addSource(isLoading) {
            checkChange()
        }
        addSource(city) {
            checkChange()
        }
        addSource(fio) {
            checkChange()
        }
        addSource(birthday) {
            checkChange()
        }
        addSource(phone) {
            checkChange()
        }
        addSource(email) {
            checkChange()
        }
        addSource(isPersonalDataChecked) {
            checkChange()
        }
    }


    /**
     * Возвращает флаг успеной отправки резюмэ.
     */
    val isQuestionnaireSendSuccess = SingleLiveEvent<Unit>()

    /**
     * Отправляет резюмэ.
     */
    fun sendQuestionnaire() {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _isLoading.postValue(false)
            mainThread {
                isQuestionnaireSendSuccess.call()
            }
        }
    }

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.getPersonalData()
                },
                onSuccess = {
                    launchMain {
                        city.value = userPreferences.city?.name ?: ""
                        fio.value = it?.fio ?: ""
                        birthday.value = it?.date ?: ""
                        phone.value = it?.phone ?: accountsPreferences.account?.phoneNumber ?: ""
                        email.value = it?.userMail?.mail ?: ""
                    }
                },
                isLoading = _isLoading
            )
        }
    }

}