package ru.apteka.loyalty_program.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import ru.apteka.loyalty_program.R
import javax.inject.Inject

/**
 * Представляет модель представления "Программа лояльности".
 */
@HiltViewModel
class LoyaltyProgramViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService,
    @ApplicationContext context: Context
) : BaseViewModel(
    navigationManager,
    messageService
) {
    /**
     * Возвращает список условий оформления программы лояльности.
     */
    val conditions: LiveData<List<String>> = MutableLiveData(
        context.resources.getStringArray(R.array.loyalty_program_conditions).toList()
    )

    private val _faq = MutableLiveData<List<Pair<String, String>>>()

    /**
     * Возвращает список вопросов.
     */
    val faq: LiveData<List<Pair<String, String>>> = _faq


    /**
     * Возвращает или устанавливает флаг согласия на обработку персональных данных.
     */
    val isPersonalData = MutableLiveData(true)



    init {
        with(context.resources) {
            val questions = getStringArray(R.array.loyalty_program_questions).toList()
            val answers = getStringArray(R.array.loyalty_program_answers).toList()
            _faq.value = questions.zip(answers)
        }


        /*viewModelScope.launchIO {
            _historyRecommendationIsLoading.postValue(true)
            delay(1500)
            _historyRecommendation.postValue(fakeHistoryRecommendation)
            _historyRecommendationIsLoading.postValue(false)
        }*/
    }
}
