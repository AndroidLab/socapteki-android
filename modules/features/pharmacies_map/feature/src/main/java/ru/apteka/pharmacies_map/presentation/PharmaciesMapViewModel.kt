package ru.apteka.pharmacies_map.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.pharmacies_map.data.model.PharmacyModel
import ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository.PharmaciesMapRepository
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки на карте".
 */
@HiltViewModel
class PharmaciesMapViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val pharmaciesMapRepository: PharmaciesMapRepository,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

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
                        it.title.contains(searchQuery.value!!, true)
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