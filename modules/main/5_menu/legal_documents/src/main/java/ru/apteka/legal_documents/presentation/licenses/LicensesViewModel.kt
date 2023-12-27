package ru.apteka.legal_documents.presentation.licenses

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.licenses.data.model.LicenseModel
import javax.inject.Inject


/**
 * Представляет модель представления "Лицензии и соглашения".
 */
@HiltViewModel
class LicensesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: MessageNoticeService,
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращает лицензии.
     */
    val licenses = listOf(
        LicenseModel(
            title = "Разрешение на осуществление розничной торговли"
        ) {
            getDocument()
        },
        LicenseModel(
            title = "Разрешение на осуществление розничной торговли"
        ) {
            getDocument()
        },
        LicenseModel(
            title = "Разрешение на осуществление розничной торговли"
        ) {
            getDocument()
        },
        LicenseModel(
            title = "Разрешение на осуществление розничной торговли"
        ) {
            getDocument()
        },
        LicenseModel(
            title = "Разрешение на осуществление розничной торговли"
        ) {
            getDocument()
        },
    )

    private val _documentFlow = MutableSharedFlow<Boolean>()

    /**
     * Возвращает документ для отображения.
     */
    val documentFlow = _documentFlow.asSharedFlow()

    private fun getDocument() {
        viewModelScope.launchIO {
            //_isLoading.postValue(true)
            //delay(1500)
            _documentFlow.emit(true)
            //_isLoading.postValue(false)
        }
    }

    init {

    }

}