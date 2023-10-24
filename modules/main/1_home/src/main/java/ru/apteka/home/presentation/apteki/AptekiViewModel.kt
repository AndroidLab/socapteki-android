package ru.apteka.home.presentation.apteki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.home.data.models.AptekaCardModel
import ru.apteka.home.data.models.AptekaModel
import ru.apteka.home.data.repository.apteki.AptekiRepository
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки".
 */
@HiltViewModel
class AptekiViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val aptekiRepository: AptekiRepository,
    navigationManager: NavigationManager,
    accountsPreferences: AccountsPreferences
) : MainScreenBaseViewModel(
    accountsPreferences,
    navigationManager
) {

    private val _apteki = MutableLiveData<List<AptekaCardModel>>(emptyList())

    /**
     * Возвращает список аптек.
     */
    val apteki: LiveData<List<AptekaCardModel>> = _apteki

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { aptekiRepository.getApteki() },
                onSuccess = { apteki ->
                    mainThread {
                        _apteki.value = apteki.map { apteka ->
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