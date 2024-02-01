package ru.apteka.profile.presentation.pharmacies

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.models.PharmacyFavoriteModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.pharmacy_favorite_service.PharmacyFavoriteService
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.profile.data.models.ProfilePharmacyCardModel
import javax.inject.Inject


/**
 * Представляет модель представления "Аптеки".
 */
@HiltViewModel
class PharmaciesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val pharmacyFavoriteService: PharmacyFavoriteService,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает список аптек.
     */
    val pharmacies: LiveData<List<ProfilePharmacyCardModel>> = pharmacyFavoriteService.pharmacies.map {
        it.map { pharmacy ->
            ProfilePharmacyCardModel(pharmacy).apply {
                favorite = PharmacyFavoriteModel(
                    favoriteService = pharmacyFavoriteService,
                    isFavorite = pharmacyFavoriteService.isContainsInFavorite(pharmacy.id),
                )
            }
        }
    }

}