package ru.apteka.making_order.presentation.making_order_recipients

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Оформление заказа, добавление получателей".
 */
@HiltViewModel
class MakingOrderRecipientsViewModel @Inject constructor(
    navigationManager: NavigationManager,
    messageNoticeService: MessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    /**
     * Возвращает ФИО получателя.
     */
    val recipientFio = MutableLiveData("")

    /**
     * Возвращает номер получателя.
     */
    val recipientNumber = MutableLiveData("")

    /**
     * Возвращает флаг заполнености полей.
     */
    val isFieldsFilled = MediatorLiveData<Boolean>().apply {
        fun checkFieldFilled() {
            value = recipientFio.value!!.isNotEmpty() && recipientNumber.value!!.length == 18
        }

        addSource(recipientFio) {
            checkFieldFilled()
        }

        addSource(recipientNumber) {
            checkFieldFilled()
        }
    }

    init {

    }

}