package ru.apteka.work_with_us.presentation.work_with_us_job_openings_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.work_with_us.data.model.JobOpeningModel
import ru.apteka.work_with_us.data.model.WORK_WITH_US_JOB_OPENING
import javax.inject.Inject

/**
 * Представляет модель представления "Работа у нас, Вакансии".
 */
@HiltViewModel
class WorkWithUsJobOpeningsDetailsViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    private val userPreferences: UserPreferences,
    private val savedStateHandle: SavedStateHandle,
    navigationManager: NavigationManager,
    messageService: IMessageService
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает или устанавливает данные о вакансии.
     */
    val jobOpening = MutableLiveData(
        savedStateHandle.get<JobOpeningModel>(WORK_WITH_US_JOB_OPENING)!!
    )

    val responsibilities = listOf(
        "Консультирование клиентов по лекарственным препаратам;",
        "Реализация лекарственных средств и сопутствующих товаров;",
        "Обеспечение и соблюдение фармацевтического порядка и санитарно-гигиенического режима на рабочем месте;",
        "Выполнение плана продаж по лекарственным препаратам.",
    )

    val requirements = listOf(
        "Фармацевтическое образование (средне-профессиональное, высшее;",
        "Опыт работы желателен;",
        "Доброжелательность;",
        "Желание оказывать качественную консультацию клиентам - ваш плюс.",
    )

    val conditions = listOf(
        "Официальное трудоустройство, соц. пакет",
        "Высокую заработную плату;",
        "Официальное трудоустройство, соц. пакет; Высокую заработную плату; Обучение, повышение квалификации, продление сертификата, медицинский осмотр, спец.одежда за счет работодателя;Возможность работать в аптеке рядом с домом.",
        "Возможность работать в аптеке рядом с домом.",
    )

    val skills = listOf(
        "Грамотная речь;",
        "Консультирование клиентов;",
        "Навыки продаж;",
        "Уверенный пользователь ПК;",
        "Клиентоориентированность.",
    )

    init {
        viewModelScope.launchIO {
            launchIO {
                /*requestHandler.handleApiRequest(
                    onRequest = { ordersRepository.getOrders() },
                    onSuccess = { orders ->
                        _orders.postValue(orders)
                    },
                    isLoading = _isLoading
                )*/
            }
        }
    }
}
