package ru.apteka.making_order.presentation.making_order_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.making_order.data.model.DeliveryTimeModel
import java.util.Calendar
import javax.inject.Inject


/**
 * Представляет модель представления "Оформление заказа выбор адресса доставки".
 */
@HiltViewModel
class MakingOrderAddressViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val savedStateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository,
    val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageService: MessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает список улиц.
     */
    val streets = listOf(
        "Мастеркова",
        "Матвеевская",
        "Матроса Железняка",
        "Матросова",
        "Матросская Тишина",
        "Матросский мост"
    )

    /**
     * Возвращает время для доставки.
     */
    val deliveryTime = DeliveryTimeModel(
        _items = listOf(
            DeliveryTimeModel.Item(
                timeFrom = "10:00",
                timeTo = "12:00"
            ),
            DeliveryTimeModel.Item(
                timeFrom = "12:00",
                timeTo = "14:00"
            ),
            DeliveryTimeModel.Item(
                timeFrom = "14:00",
                timeTo = "17:00"
            )
        )
    ) { item ->
        selectedDeliveryTime.value = item
    }

    /**
     * Возвращает или устанавливает выбранную улицу.
     */
    val selectedStreet = MutableLiveData("")

    /**
     * Возвращает или устанавливает выбранный дом.
     */
    val selectedHome = MutableLiveData("")

    /**
     * Возвращает или устанавливает выбранный этаж.
     */
    val selectedFloor = MutableLiveData("")

    /**
     * Возвращает или устанавливает выбранный подьезд.
     */
    val selectedEntrance = MutableLiveData("")

    /**
     * Возвращает или устанавливает выбранную квартиру/офис.
     */
    val selectedFlat = MutableLiveData("")

    /**
     * Возвращает или устанавливает выбранный код домофона.
     */
    val selectedCode = MutableLiveData("")

    /**
     * Возвращает или устанавливает комментарий для курьера.
     */
    val selectedComment = MutableLiveData("")

    /**
     * Возвращает или устанавливает дату доставки.
     */
    val _selectedDeliveryDate = MutableLiveData<Calendar?>(null)

    /**
     * Возвращает или устанавливает дату доставки.
     */
    val selectedDeliveryDate: LiveData<String> = _selectedDeliveryDate.map {
        it?.let { calendar ->
            "${String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))}.${String.format("%02d", calendar.get(Calendar.MONTH) + 1)}.${calendar.get(Calendar.YEAR)}"
        } ?: ""
    }

    /**
     * Возвращает или устанавливает время доставки.
     */
    val selectedDeliveryTime = MutableLiveData<DeliveryTimeModel.Item?>(null)


    /**
     * Возвращает флаг заполнености всех полей.
     */
    val isAllFieldFilled = MediatorLiveData<Boolean>().apply {
        fun checkFieldsFilled() {
            value = selectedStreet.value!!.isNotEmpty() && selectedHome.value!!.isNotEmpty()
                    && selectedFlat.value!!.isNotEmpty() &&  _selectedDeliveryDate.value != null && selectedDeliveryTime.value != null
        }

        addSource(selectedStreet) {
            checkFieldsFilled()
        }

        addSource(selectedHome) {
            checkFieldsFilled()
        }

        addSource(selectedFlat) {
            checkFieldsFilled()
        }

        addSource(_selectedDeliveryDate) {
            checkFieldsFilled()
        }

        addSource(selectedDeliveryTime) {
            checkFieldsFilled()
        }

    }

    init {
        val selectedAddressPref = userPreferences.selectedDeliveryAddress
        selectedStreet.value = selectedAddressPref?.street ?: ""
        selectedHome.value = selectedAddressPref?.home ?: ""
        selectedFloor.value = selectedAddressPref?.floor ?: ""
        selectedEntrance.value = selectedAddressPref?.entrance ?: ""
        selectedFlat.value = selectedAddressPref?.flat ?: ""
        selectedCode.value = selectedAddressPref?.code ?: ""

        viewModelScope.launchIO {
            /*requestHandler.handleApiRequest(
                onRequest = {
                    loginRepository.getPersonalData()
                },
                onSuccess = {
                    personalData.postValue(it)
                },
                isLoading = _isPersonalDataLoading
            )*/
        }
    }

}