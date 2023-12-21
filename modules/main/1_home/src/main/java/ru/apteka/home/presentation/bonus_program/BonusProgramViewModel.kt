package ru.apteka.home.presentation.bonus_program

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.home.data.models.BonusModel
import javax.inject.Inject


/**
 * Представляет модель представления "Бонусная программа".
 */
@HiltViewModel
class BonusProgramViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {
    private val fakeBonuses = listOf(
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = 320,
            desc = "Интернет заказ"
        ),
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = 320,
            desc = "Интернет заказ"
        ),
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = -320,
            desc = "Покупка в розницу"
        ),
        BonusModel(
            title = "Начисление",
            date = 1697711146,
            value = 320,
            desc = "Интернет заказ"
        ),
        BonusModel(
            title = "Списание",
            date = 1697711146,
            value = -320,
            desc = "Покупка в розницу"
        ),
    )

    private val _bonuses = MutableLiveData<List<BonusModel>>(emptyList())

    /**
     *
     */
    val bonuses: LiveData<List<BonusModel>> = _bonuses

    init {
        viewModelScope.launchIO {
            _isLoading.postValue(true)
            delay(1500)
            _bonuses.postValue(fakeBonuses)
            _isLoading.postValue(false)
        }
    }


    /**
     * Сохранить.
     */
    fun save() {

    }

}