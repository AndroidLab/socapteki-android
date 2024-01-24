package ru.apteka.contacts.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.contacts.data.models.ContactsWorkingConditionModel
import javax.inject.Inject


/**
 * Представляет модель представления "Контакты".
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: MessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    val workingConditionsFake = listOf(
        ContactsWorkingConditionModel(
            title = "Результаты проведения специальной оценки условий труда",
            onClick = {

            }
        ),
        ContactsWorkingConditionModel(
            title = "Результаты проведения специальной оценки условий труда",
            onClick = {

            }
        ),
        ContactsWorkingConditionModel(
            title = "Результаты проведения специальной оценки условий труда",
            onClick = {

            }
        ),
        ContactsWorkingConditionModel(
            title = "Результаты проведения специальной оценки условий труда",
            onClick = {

            }
        ),
        ContactsWorkingConditionModel(
            title = "Результаты проведения специальной оценки условий труда",
            onClick = {

            }
        ),
    )



    init {

    }

}