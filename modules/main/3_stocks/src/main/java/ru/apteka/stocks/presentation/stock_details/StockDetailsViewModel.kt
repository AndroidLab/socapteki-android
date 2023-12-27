package ru.apteka.stocks.presentation.stock_details

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.stocks.data.models.StockModel
import javax.inject.Inject


/**
 * Представляет модель представления "Детали акции".
 */
@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val basketService: BasketService,
    val favoriteService: FavoriteService,
    navigationManager: NavigationManager,
    messageNoticeService: IMessageNoticeService
) : BaseViewModel(
    navigationManager,
    messageNoticeService
) {

    /**
     * Возвращает или устанавливает акцию.
     */
    val stock: MutableLiveData<StockModel> = MutableLiveData(null)

    init {
        //searchSocks()
    }


}