package ru.apteka.profile.presentation.profile_notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.ProductFavoriteModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.favorites_service.FavoriteService
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.getProductsFake2
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.profile.data.models.ProductNotificationModel
import javax.inject.Inject


/**
 * Представляет модель представления "Персональные данные, Уведомления о товарах".
 */
@HiltViewModel
class ProfileNotificationsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val favoriteService: FavoriteService,
    messageService: IMessageService,
    navigationManager: NavigationManager,
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает уведомления о продукте.
     */
    val notifications = ScopedLiveData<ViewModel, List<ProductNotificationModel>>(emptyList())

    init {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            notifications.postValue(
                getProductsFake2().map { product ->
                    ProductNotificationModel(
                        product
                    ).apply {
                        favoriteModel = ProductFavoriteModel(
                            favoriteService = favoriteService,
                            isFavorite = favoriteService.isContainsInFavorite(product.id)
                        )
                        onNotificationRemove = { product ->
                            notifications.postValue(
                                notifications.getValue()!!.minus(
                                    notifications.getValue()!!.single { it.product.id == product.id }
                                )
                            )
                        }
                    }
                }
            )
            isLoading.postValue(false)
        }
    }

}
