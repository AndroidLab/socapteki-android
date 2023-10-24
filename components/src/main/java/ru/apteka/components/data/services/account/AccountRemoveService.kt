package ru.apteka.components.data.services.account

import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис отслеживания удаления аккаунта
 */
@Singleton
class AccountRemoveService @Inject constructor() {
    /**
     * Отправляет событие удаления аккаунта.
     */
    val isAccountRemove = SingleLiveEvent<Unit>()
}