package ru.apteka.home.presentation.apteki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.home.data.models.AptekaCardModel
import ru.apteka.home.data.repository.apteki.AptekiRepository
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки".
 */
@HiltViewModel
class PharmaciesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val aptekiRepository: AptekiRepository,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : MainScreenBaseViewModel(
    navigationManager,
    messageNoticeService
) {

    private val _pharmacies = MutableLiveData<List<AptekaCardModel>>(emptyList())

    /**
     * Возвращает список аптек.
     */
    val pharmacies: LiveData<List<AptekaCardModel>> = _pharmacies

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { aptekiRepository.getApteki() },
                onSuccess = { pharmacies ->
                    mainThread {
                        _pharmacies.value = pharmacies.map { apteka ->
                            AptekaCardModel(
                                apteka = apteka,
                                onFavoriteClick = {

                                }
                            )
                        }
                    }
                },
                isLoading = _isLoading
            )
        }
    }

}