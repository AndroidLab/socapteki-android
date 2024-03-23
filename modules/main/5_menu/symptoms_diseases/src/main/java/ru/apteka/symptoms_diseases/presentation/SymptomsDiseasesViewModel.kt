package ru.apteka.symptoms_diseases.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.debounce
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.symptoms_diseases.data.model.SymptomModel
import javax.inject.Inject


/**
 * Представляет модель представления "Симптомы и болезни".
 */
@HiltViewModel
class SymptomsDiseasesViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {

    private val fakeSymptoms = listOf(
        SymptomModel("Аменория"),
        SymptomModel("Аменория"),
        SymptomModel("Аменория"),
        SymptomModel("Аменория"),
        SymptomModel("Аменория"),
        SymptomModel("Аменория"),
        SymptomModel("Аменория"),
        SymptomModel("Бменория"),
        SymptomModel("Бменория"),
        SymptomModel("Бменория"),
        SymptomModel("Бменория"),
        SymptomModel("Бменория"),
        SymptomModel("Бменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Вменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Гменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
        SymptomModel("Дменория"),
    )

    private val symptoms = MutableLiveData<List<Any>>(emptyList())

    /**
     * Возвращает список симптомов.
     */
    val letters = ScopedLiveData(emptyList<Any>())

    /**
     * Возвращает флаг прогресса поиска.
     */
    val isSearchProgress = ScopedLiveData(false)

    /**
     * Возвращает или устанавливает значение для поиска.
     */
    var valueQuery = ""

    /**
     * Возвращает текст поиска.
     */
    val onTextChange = viewModelScope.debounce<String>(1500L) { value ->
        if (value.isNotBlank()) {
            if (value != valueQuery) {
                valueQuery = value
                symptomsSearch(value)
            }
        } else {
            valueQuery = ""
            symptomsHandler(fakeSymptoms)
        }
    }


    private suspend fun symptomsSearch(value: String) {
        viewModelScope.launchIO {
            isSearchProgress.postValue(true)
            delay(1500)
            symptomsHandler(fakeSymptoms.filter { it.title.contains(value, true) })
            isSearchProgress.postValue(false)
        }
    }

    /**
     * Возвращает симптомы.
     */
    private fun getSymptoms() {
        viewModelScope.launchIO {
            isLoading.postValue(true)
            delay(1500)
            symptomsHandler(fakeSymptoms)
            isLoading.postValue(false)
        }
    }

    private fun symptomsHandler(list: List<SymptomModel>) {
        symptoms.postValue(
            buildList {
                list.forEachIndexed { index, symptom ->
                    if (index == 0 || list[index-1].title.first() != symptom.title.first()) {
                        add(symptom.title.first().toString())
                    }
                    add(symptom)
                }
            }
        )
    }

    init {
        getSymptoms()
    }

}