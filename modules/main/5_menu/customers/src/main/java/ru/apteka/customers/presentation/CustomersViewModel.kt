package ru.apteka.customers.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject

/**
 * Представляет модель представления "Покупателям".
 */
@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: MessageService,
) : BaseViewModel(
    navigationManager,
    messageService
)
