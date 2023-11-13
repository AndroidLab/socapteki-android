package ru.apteka.pharmacies_map.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.components.data.models.PharmacyModel
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.asLiveData
import ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository.PharmaciesMapRepository
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки на карте".
 */
@HiltViewModel
class PharmaciesMapViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val pharmaciesMapRepository: PharmaciesMapRepository,
    private val savedStateHandle: SavedStateHandle,
    val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращает тип взаимодействия с картой.
     */
    val typeInteraction = MutableLiveData(savedStateHandle.get<TypeInteraction>(PHARMACIES_MAP_TYPE_INTERACTION)!!).asLiveData()

    /**
     * Возвращает список аптек.
     */
    val pharmacies = MutableLiveData<List<PharmacyModel>>(emptyList())

    /**
     * Возвращает или устанавливает номер заказа для поиска.
     */
    val searchQuery = MutableLiveData("")

    /**
     * Возвращает фильтрованный список аптек.
     */
    val pharmaciesFiltered = MediatorLiveData<List<PharmacyModel>>().apply {
        fun filterPharmacies() {
            postValue(
                if (searchQuery.value!!.isEmpty()) {
                    pharmacies.value!!
                } else {
                    pharmacies.value!!.filter {
                        it.name.contains(searchQuery.value!!, true)
                    }
                }
            )
        }

        addSource(pharmacies) {
            filterPharmacies()
        }

        addSource(searchQuery) {
            filterPharmacies()
        }
    }

    /**
     * Возвращает выбранную аптеку.
     */
    val selectedPharmacy = userPreferences.selectedPharmacyFlow.asLiveData()

    /**
     * Сохраняет выбранную аптеку.
     */
    fun savePharmacy(pharmacy: PharmacyModel) {
        userPreferences.selectedPharmacy = pharmacy
        navigationManager.generalNavController.popBackStack()
    }

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    pharmaciesMapRepository.getPharmacies()
                },
                onSuccess = { orders ->
                    pharmacies.postValue(orders)
                },
                isLoading = _isLoading
            )
        }
    }

}