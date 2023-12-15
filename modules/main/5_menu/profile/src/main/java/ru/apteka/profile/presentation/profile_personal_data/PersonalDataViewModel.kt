package ru.apteka.profile.presentation.profile_personal_data

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.DownTimer
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.validateEmail
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.profile.R
import ru.apteka.profile.data.models.SexModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Представляет модель представления "Персональные данные".
 */
@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    messageNoticeService: IMessageNoticeService,
    navigationManager: NavigationManager,
    accountsPreferences: AccountsPreferences
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    private val personalData = MutableLiveData<PersonalData?>(null)

    /**
     * Устанавливает или возвращает ФИО.
     */
    val fio = MutableLiveData<String?>(null)

    private var _date: String? = null

    /**
     * Устанавливает или возвращает дату рождения.
     */
    val date = MutableLiveData<String?>(null)

    /**
     * Устанавливает или возвращает номер.
     */
    val phone = MutableLiveData("")

    /**
     * Устанавливает или возвращает емайл.
     */
    val _email = MutableLiveData<PersonalData.UserMail?>(null)

    val email = _email.map {
        it?.mail
    }

    /**
     * Возвращает флаг верефицированого емайл.
     */
    val isEmailVerified = MutableLiveData(false)

    private val _sex = MutableLiveData<Int?>(null)

    /**
     * Возвращает пол.
     */
    val sex: LiveData<Int?> = _sex

    private val _sexIsLoading = MutableLiveData(false)
    private var sexSaveError = false

    /**
     * Возвращает флаг загрузки пола.
     */
    val sexIsLoading: LiveData<Boolean> = _sexIsLoading

    val sexModel = SexModel(
        _items = listOf(SexModel.Item(1), SexModel.Item(2))
    ) {
        if (sexSaveError) {
            sexSaveError = false
        } else {
            saveSex(it.sex)
        }
    }

    private fun saveSex(sex: Int) {
        viewModelScope.launchIO {
            _sexIsLoading.postValue(true)
            loginRepository.savePersonalDataSex(sex)
            /*delay(1500)
            sexSaveError = true
            when(personalData.value!!.sex) {
                1 -> sexModel.items[1].isItemSelected.postValue(true)
                2 -> sexModel.items[0].isItemSelected.postValue(true)
                null -> {
                    sexModel.items[0].isItemSelected.postValue(false)
                    sexModel.items[1].isItemSelected.postValue(false)
                }
            }*/
            _sexIsLoading.postValue(false)
        }
    }

    /**
     * Возвращает или устанавливает флаг отправки электронных чеков.
     */
    val isReceiveReceipts = MutableLiveData(false)


    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.getPersonalData()
                },
                onSuccess = {
                    personalData.postValue(it)
                    launchMain {
                        fio.value = it.fio
                        date.value = _date
                        phone.value = it.phone ?: accountsPreferences.account!!.phoneNumber!!
                        _email.value = it.userMail
                        _sex.value = it.sex
                        isEmailVerified.value = it.userMail?.isVerified ?: false
                        isReceiveReceipts.value = it.isReceiveReceipts
                    }
                },
                isLoading = _isLoading
            )
        }
    }

}