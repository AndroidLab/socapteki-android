package ru.apteka.about_company.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "".
 */
@HiltViewModel
class AboutCompanyViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {


    init {

    }

}