package ru.apteka.personal_data.presentation.personal_data

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.DownTimer
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.personal_data.R
import java.text.DecimalFormat
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Представляет модель представления "Персональные данные".
 */
@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    val messageNoticeService: IMessageNoticeService,
    navigationManager: NavigationManager,
    accountsPreferences: AccountsPreferences
) : BaseViewModel(
    navigationManager
) {
    companion object {
        private val VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    }

    private val downTimer = DownTimer(60)
    private val _leftTime = MutableLiveData<String?>(null)

    /**
     * Осталось времени до повторения отправки кода.
     */
    val leftTime: LiveData<String?> = _leftTime

    private var _fio = ""

    /**
     * Устанавливает или возвращает ФИО.
     */
    val fio = MutableLiveData("")

    private var _date = ""

    /**
     * Устанавливает или возвращает дату рождения.
     */
    val date = MutableLiveData("")

    private var _phone = ""

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


    private var _email = ""
    private val _isEmailValidAndVerified = MutableLiveData(false)

    /**
     * Устанавливает или возвращает емайл.
     */
    val email = MutableLiveData("")

    /**
     * Возвращает флаг верефицированого емайл.
     */
    val isEmailVerified = MutableLiveData(false)

    /**
     * Возвращает ошибку валидации или верификации.
     */
    val emailVerifiedAndValid = MediatorLiveData<Int?>().apply {
        fun checkMailValidAndVerified() {
            postValue(
                when (true) {
                    !emailValidate(email.value!!) -> R.string.personal_data_mail_not_valid
                    isEmailVerified.value!! -> R.string.personal_data_mail_not_ferified
                    else -> null
                }
            )
        }

        addSource(isEmailVerified) {
            checkMailValidAndVerified()
        }
        addSource(email) {
            checkMailValidAndVerified()
        }
    }

    private var _sex = 0

    /**
     * Возвращает или устанавливает выбор мужского пола.
     */
    val male = MutableLiveData(false)

    /**
     * Возвращает или устанавливает выбор женского пола.
     */
    val female = MutableLiveData(false)

    private var _isReceiveReceipts = false

    /**
     * Возвращает или устанавливает флаг отправки электронных чеков.
     */
    val isReceiveReceipts = MutableLiveData(false)

    /**
     * Возвращает или устанавливает флаг, что произошли изменения.
     */
    val isChanged = MediatorLiveData<Boolean>().apply {
        fun checkChange() {
            postValue(
                !isLoading.value!! && emailValidate(email.value!!) && (fio.value!! != _fio || date.value!! != _date || getPhoneRaw() != _phone || email.value!! != _email ||
                        (male.value!! && (_sex == 0 || _sex == 2)) || (female.value!! && (_sex == 0 || _sex == 1)) ||
                        isReceiveReceipts.value!! != _isReceiveReceipts
                        )
            )
        }

        addSource(isLoading) {
            checkChange()
        }
        addSource(fio) {
            checkChange()
        }
        addSource(date) {
            checkChange()
        }
        addSource(phone) {
            checkChange()
        }
        addSource(email) {
            checkChange()
        }
        addSource(male) {
            checkChange()
        }
        addSource(female) {
            checkChange()
        }
        addSource(isReceiveReceipts) {
            checkChange()
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
                        _fio = it?.fio ?: ""
                        _date = it?.date ?: ""
                        _phone = it?.phone ?: accountsPreferences.account!!.phoneNumber!!
                        _email = it?.userMail?.mail ?: ""
                        _sex = it?.sex ?: 0
                        _isReceiveReceipts = it?.isReceiveReceipts ?: false

                        fio.value = _fio
                        date.value = _date
                        phone.value = _phone
                        email.value = _email
                        male.value = _sex == 1
                        female.value = _sex == 2
                        isReceiveReceipts.value = _isReceiveReceipts
                        isEmailVerified.value = it?.userMail?.isVerified ?: false
                    }
                },
                isLoading = _isLoading
            )
        }
    }

    /**
     * Получает код повторно.
     */
    val onNewCode: (View) -> Unit = {
        if (leftTime.value == null) {
            viewModelScope.launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { loginRepository.getNewCode("kkhghk") },
                    isLoading = _isLoading
                )
            }
            startDownTime()
        }
    }

    /**
     * Сохраняет персональные данные
     */
    fun savePersonalData() {
        _fio = fio.value!!
        _date = date.value!!
        _phone = getPhoneRaw()
        _email = email.value!!
        _sex = when (true) {
            (!male.value!! && !female.value!!) -> 0
            male.value!! -> 1
            female.value!! -> 2
            else -> throw IllegalArgumentException()
        }
        _isReceiveReceipts = isReceiveReceipts.value!!

        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.savePersonalData(
                        PersonalData(
                            fio = _fio,
                            date = _date,
                            phone = _phone,
                            userMail = PersonalData.UserMail(
                                mail = _email,
                                isVerified = isEmailVerified.value!!
                            ),
                            sex = _sex,
                            isReceiveReceipts = _isReceiveReceipts
                        )
                    )
                },
                isLoading = _isLoading
            )
        }
    }

    private fun startDownTime() {
        viewModelScope.launchIO {
            downTimer.startWithFlow().map {
                it?.let {
                    val minutes = it / 60
                    val seconds = it % 60
                    val format = DecimalFormat("00")
                    "${format.format(minutes)}:${format.format(seconds)}"
                }
            }.collect {
                _leftTime.postValue(it)
            }
        }
    }

    private fun emailValidate(email: String) =
        if (email.isEmpty()) true else VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches()
}