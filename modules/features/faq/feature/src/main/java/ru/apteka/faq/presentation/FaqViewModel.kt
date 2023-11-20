package ru.apteka.faq.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.faq.data.model.FaqModel
import ru.apteka.faq.data.repository.faq.FaqRepository
import javax.inject.Inject


/**
 * Представляет модель представления "FAQ".
 */
@HiltViewModel
class FaqViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val faqRepository: FaqRepository,
    navigationManager: NavigationManager,
    messageNoticeService: MessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    private val _faq = MutableLiveData<List<FaqModel>>(emptyList())

    /**
     * Возвращет faq.
     */
    val faq: LiveData<List<FaqModel>> = _faq

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { faqRepository.getFaq() },
                onSuccess = {
                    _faq.postValue(it)
                },
                isLoading = _isLoading
            )
        }
    }

}