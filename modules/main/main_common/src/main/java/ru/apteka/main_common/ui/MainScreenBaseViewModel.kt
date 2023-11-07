package ru.apteka.main_common.ui

import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel


/**
 * Представляет модель представления "Базовый экран для главных экранов".
 */
abstract class MainScreenBaseViewModel(
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

}