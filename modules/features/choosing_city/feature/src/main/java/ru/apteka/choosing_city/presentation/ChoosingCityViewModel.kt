package ru.apteka.choosing_city.presentation

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.choosing_city.data.model.CityCardDetectModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.choosing_city.data.model.CityCardModel
import ru.apteka.choosing_city.data.repository.new_repository.CitiesRepository
import ru.apteka.components.data.services.message_notice_service.IMessageService
import javax.inject.Inject


/**
 * Представляет модель представления "Выбор города".
 */
@HiltViewModel
class ChoosingCityViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val citiesRepository: CitiesRepository,
    private val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает пункт модель пункта списка для автоопределения города.
     */
    val cityDetect = CityCardDetectModel {
        Log.d("myL", "cityDetect")
    }

    private val _cities = MutableLiveData<List<CityCardModel>>(emptyList())

    /**
     * Возвращает или устанавлитвает текст поиска города.
     */
    val cityQuery = MutableLiveData("")

    val citiesFilteredMediator = MediatorLiveData<List<CityCardModel>>().apply {
        fun filterCities() {
            postValue(_cities.value!!.filter { it.city.name.contains(cityQuery.value!!, true) })
        }

        addSource(_cities) {
            filterCities()
        }

        addSource(cityQuery) {
            filterCities()
        }
    }

    init {
        viewModelScope.launchIO {
            launchIO {
                requestHandler.handleApiRequest(
                    onRequest = { citiesRepository.getCities() },
                    onSuccess = { cities ->
                        _cities.postValue(
                            cities.map { city ->
                                CityCardModel(
                                    city = city,
                                    onItemClick = {
                                        userPreferences.city = it.city
                                        navigationManager.generalNavController.popBackStack()
                                    }
                                )
                            }.onEach {
                                if (userPreferences.city?.name == it.city.name) {
                                    it.isSelected.postValue(true)
                                }
                            }
                        )
                    },
                    isLoading = _isLoading
                )
            }
        }
    }

}