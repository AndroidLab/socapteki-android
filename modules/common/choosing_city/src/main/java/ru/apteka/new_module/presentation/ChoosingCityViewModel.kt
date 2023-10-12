package ru.apteka.new_module.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.common.data.RequestHandler
import ru.apteka.common.data.utils.launchIO
import ru.apteka.common.ui.BaseViewModel
import ru.apteka.components.data.navigation_manager.INavigationManager
import ru.apteka.components.data.navigation_manager.NavigationManager
import ru.apteka.components.data.services.city.CityPreferences
import ru.apteka.new_module.data.model.CityCardModel
import ru.apteka.new_module.data.repository.new_repository.CitiesRepository
import javax.inject.Inject


/**
 * Представляет модель представления "Выбор города".
 */
@HiltViewModel
class ChoosingCityViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val citiesRepository: CitiesRepository,
    private val cityPreferences: CityPreferences,
    private val navigationManager: NavigationManager
) : BaseViewModel() {

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
                                        cityPreferences.city = it.city
                                        navigationManager.generalNavController.popBackStack()
                                    }
                                )
                            }.onEach {
                                if (cityPreferences.city?.name == it.city.name) {
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

    /**
     * Очищает поле поиска.
     */
    fun clearQuery() {
        cityQuery.value = ""
    }

}