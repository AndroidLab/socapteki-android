package ru.apteka.contacts.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Контакты".
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
	navigationManager: NavigationManager,
    messageNoticeService: MessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {


    init {

    }

}