package ru.apteka.profile.presentation.profile_personal_data_fio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Персональные данные, изменить ФИО".
 */
@HiltViewModel
class PersonalDataFioViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val loginRepository: LoginRepository,
    messageService: IMessageService,
    navigationManager: NavigationManager,
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает или устанавливает адрес почты.
     */
    val fio = MutableLiveData("")

    /**
     * Сохраняет персональные данные.
     */
    fun savePersonalData(success: () -> Unit) {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.savePersonalDataMail(
                        fio.value!!
                    )
                },
                onSuccess = {
                    mainThread {
                        success()
                    }
                },
                isLoading = _isLoading
            )
        }
    }
}

