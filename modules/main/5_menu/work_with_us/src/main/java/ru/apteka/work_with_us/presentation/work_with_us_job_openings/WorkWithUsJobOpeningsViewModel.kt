package ru.apteka.work_with_us.presentation.work_with_us_job_openings

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.work_with_us.R
import ru.apteka.work_with_us.data.model.EmployeeReviewModel
import ru.apteka.work_with_us.data.model.JobOpeningModel
import ru.apteka.work_with_us.data.model.JobOpeningsFilterModel
import javax.inject.Inject
import ru.apteka.components.R as ComponentsR

/**
 * Представляет модель представления "Работа у нас, Вакансии".
 */
@HiltViewModel
class WorkWithUsJobOpeningsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val userPreferences: UserPreferences,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращат модель фильтра.
     */
    val jobOpeningsFilter = JobOpeningsFilterModel(
        _items = listOf(
            JobOpeningsFilterModel.Item(
                title = "Все города"
            ),
            JobOpeningsFilterModel.Item(
                title = "Москва"
            ),
            JobOpeningsFilterModel.Item(
                title = "Ростов-на Дону"
            ),
            JobOpeningsFilterModel.Item(
                title = "Батайск"
            ),
        )
    ) {
        userPreferences.jobOpeningsCityFilter = it?.title ?: "null"
    }.apply {
        setItemSelected(0)
    }

    /**
     * Возвращает список всех вакансий.
     */
    val jobOpenings = ScopedLiveData(emptyList<JobOpeningModel>())

    /**
     * Возвращает фильтрованный список заказов.
     */
    val jobOpeningsFiltered = MediatorLiveData<List<JobOpeningModel>>().apply {

        fun filterJobOpenings() {
            postValue(
                when (userPreferences.jobOpeningsCityFilter) {
                    "Все города" -> jobOpenings.value
                    "Москва" -> jobOpenings.value!!.filter { it.city == userPreferences.jobOpeningsCityFilter }
                    "Ростов-на Дону" -> jobOpenings.value!!.filter { it.city == userPreferences.jobOpeningsCityFilter }
                    "Батайск" -> jobOpenings.value!!.filter { it.city == userPreferences.jobOpeningsCityFilter }
                    else -> {
                        throw IllegalArgumentException()
                    }
                }?.sortedByDescending { it.city }
            )
        }

        addSource(jobOpenings) {
            filterJobOpenings()
        }

        addSource(userPreferences.jobOpeningsCityFilterFlow.asLiveData()) {
            filterJobOpenings()
        }

        jobOpeningsFilter.items.forEach {
            addSource(it.isItemSelected) {
                filterJobOpenings()
            }
        }
    }


    /**
     *
     */
    val events = ScopedLiveData(emptyList<Int>())

    /**
     *
     */
    val eventsIsLoading = ScopedLiveData(false)

    /**
     *
     */
    val employeeReviews = ScopedLiveData(emptyList<EmployeeReviewModel>())

    /**
     *
     */
    val employeeReviewsIsLoading = ScopedLiveData(false)


    init {
        viewModelScope.launchIO {
            launchIO {
                eventsIsLoading.postValue(true)
                delay(1500)
                events.postValue(
                    listOf(
                        ComponentsR.drawable.banner,
                        ComponentsR.drawable.banner,
                        ComponentsR.drawable.banner
                    )
                )
                eventsIsLoading.postValue(false)
            }

            launchIO {
                employeeReviewsIsLoading.postValue(true)
                delay(1500)
                employeeReviews.postValue(
                    listOf(
                        EmployeeReviewModel(
                            photo = R.drawable.emploee_photo,
                            post = "Территориальный менеджер",
                            name = "Веременко Роман",
                            desc = "В декабре 2013 я пришел в компанию “Социальная аптека”, уже имя большой опыт работы в фарм. сфере."
                        ),
                        EmployeeReviewModel(
                            photo = R.drawable.emploee_photo,
                            post = "Территориальный менеджер",
                            name = "Веременко Роман",
                            desc = "В декабре 2013 я пришел в компанию “Социальная аптека”, уже имя большой опыт работы в фарм. сфере."
                        ),
                        EmployeeReviewModel(
                            photo = R.drawable.emploee_photo,
                            post = "Территориальный менеджер",
                            name = "Веременко Роман",
                            desc = "В декабре 2013 я пришел в компанию “Социальная аптека”, уже имя большой опыт работы в фарм. сфере."
                        ),
                    )
                )
                employeeReviewsIsLoading.postValue(false)
            }
        }
    }
}
