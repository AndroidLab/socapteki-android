package ru.apteka.pharmacies_map.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.R
import ru.apteka.components.data.models.PharmacyCardModel
import ru.apteka.components.data.models.PharmacyFavoriteModel
import ru.apteka.components.data.models.PharmacyModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.pharmacy_favorite_service.PharmacyFavoriteService
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.pharmacies_map.data.models.MapAddressModel
import ru.apteka.pharmacies_map.data.repository.pharmacies_map_repository.PharmaciesMapRepository
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import javax.inject.Inject

/**
 * Представляет модель представления "Аптеки на карте".
 */
@HiltViewModel
class PharmaciesMapViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val pharmaciesMapRepository: PharmaciesMapRepository,
    private val pharmacyFavoriteService: PharmacyFavoriteService,
    private val savedStateHandle: SavedStateHandle,
    val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    private val addressFake = listOf(
        MapAddressModel(
            title = "Мастеркова, улица",
            onItemClick = {

            }
        ),
        MapAddressModel(
            title = "Матвеевская улица",
            onItemClick = {

            }
        ),
        MapAddressModel(
            title = "Матроса Железняка, бульвар",
            onItemClick = {

            }
        ),
        MapAddressModel(
            title = "Матросова, улица",
            onItemClick = {

            }
        ),
        MapAddressModel(
            title = "Матросская Тишина, улица",
            onItemClick = {

            }
        ),
        MapAddressModel(
            title = "Матросский мост",
            onItemClick = {

            }
        ),
    )

    /**
     * Возвращает выбор круглосуточных аптек.
     */
    val isRoundClockSelected = ScopedLiveData(false)

    /**
     * Обрабатывает клик по кнопку 'Круглосуточные'.
     */
    fun onRoundClockClick() {
        isRoundClockSelected.setValue(!isRoundClockSelected.value!!)
    }

    /**
     * Возвращает выбор моих аптек.
     */
    val isMyPharmaciesSelected = ScopedLiveData(false)

    /**
     * Обрабатывает клик по кнопку 'Мои аптеки'.
     */
    fun onMyPharmaciesClick() {
        isMyPharmaciesSelected.setValue(!isMyPharmaciesSelected.value!!)
        isMyPharmaciesSelected.setValue(!isMyPharmaciesSelected.value!!)
    }

    /**
     * Возвращает список аптек.
     */
    val pharmacies = ScopedLiveData<ViewModel, List<PharmacyCardModel>>(emptyList())

    /**
     * Сохраняет выбранную аптеку.
     */
    fun savePharmacy(pharmacy: PharmacyModel) {
        userPreferences.selectedPharmacy = pharmacy
        navigationManager.generalNavController.popBackStack()
    }

    /**
     * Возвращает выбранный город.
     */
    val city = userPreferences.cityFlow.asLiveData()

    /**
     * Возвращает или устанавлитвает текст поиска адреса.
     */
    val addressQuery = MutableLiveData("")

    /**
     * Возвращает список адресов.
     */
    val addresses = addressQuery.map { query ->
        if (query.isEmpty()) {
            emptyList()
        } else {
            addressFake.filter {
                it.title.contains(query, true)
            }
        }
    }

    /**
     * Возвращает или устанавлитвает цвет текста поиска адреса.
     */
    val addressQueryColor = MediatorLiveData<Int>().apply {
        fun setAddressQueryColor() {
            value =
                if (addressQuery.value!!.isEmpty() || addresses.value!!.isNotEmpty() || isLoading.value!!) {
                    R.color.dark_black
                } else {
                    R.color.red
                }
        }

        addSource(addressQuery) {
            setAddressQueryColor()
        }

        addSource(addresses) {
            setAddressQueryColor()
        }

        addSource(isLoading) {
            setAddressQueryColor()
        }
    }

    /**
     * Возвращает тип взаимодействия с картой.
     */
    val typeInteraction = MutableLiveData<TypeInteraction>(null)

    init {
        viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = {
                    pharmaciesMapRepository.getPharmacies()
                },
                onSuccess = {
                    pharmacies.postValue(
                        it.map { pharmacy ->
                            PharmacyCardModel(
                                pharmacy = pharmacy,
                            ).apply {
                                favorite = PharmacyFavoriteModel(
                                    favoriteService = pharmacyFavoriteService,
                                    isFavorite = pharmacyFavoriteService.isContainsInFavorite(pharmacy.id),
                                )
                            }
                        }
                    )
                },
                onLoading = {
                    isLoading.postValue(it)
                }
            )
        }
    }
}