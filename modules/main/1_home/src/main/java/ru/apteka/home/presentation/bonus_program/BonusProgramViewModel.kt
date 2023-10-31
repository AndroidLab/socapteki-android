package ru.apteka.home.presentation.bonus_program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.SubscriptionsModel
import ru.apteka.components.data.repository.kogin.LoginRepository
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.home.data.models.BonusModel
import ru.apteka.home.data.repository.bonus.BonusRepository
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Бонусная программа".
 */
@HiltViewModel
class BonusProgramViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val bonusRepository: BonusRepository,
    navigationManager: NavigationManager,
    accountsPreferences: AccountsPreferences,
    messageNoticeService: IMessageNoticeService
) : MainScreenBaseViewModel(
    accountsPreferences,
    navigationManager,
    messageNoticeService
) {

    private val _bonuses = MutableLiveData<List<BonusModel>>(emptyList())

    /**
     *
     */
    val bonuses: LiveData<List<BonusModel>> = _bonuses

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { bonusRepository.getBonusHistory() },
                onSuccess = { bonuses ->
                    _bonuses.postValue(bonuses)
                },
                isLoading = _isLoading
            )
        }
    }


    /**
     * Сохранить.
     */
    fun save() {

    }

}