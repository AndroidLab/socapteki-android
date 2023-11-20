package ru.apteka.our_partners.presentation

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Наши партнеры".
 */
@HiltViewModel
class OurPartnersViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращает картинки партнеров.
     */
    val partnersIcon = MutableLiveData(
        listOf(
            Unit,
            Unit,
            Unit,
            Unit,
            Unit,
        )
    )

    init {

    }

}