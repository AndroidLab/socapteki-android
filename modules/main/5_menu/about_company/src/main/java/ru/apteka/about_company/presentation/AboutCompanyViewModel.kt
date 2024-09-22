package ru.apteka.about_company.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.about_company.R
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "".
 */
@HiltViewModel
class AboutCompanyViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    messageService: IMessageService,
    @ApplicationContext context: Context
) : BaseViewModel(
    navigationManager,
    messageService
) {

    /**
     * Возвращает список типов лекарств.
     */
    val drugs: LiveData<List<String>> = MutableLiveData(
        context.resources.getStringArray(R.array.about_company_drugs).toList()
    )

    /**
     * Возвращает список удобств.
     */
    val conveniences: LiveData<List<String>> = MutableLiveData(
        context.resources.getStringArray(R.array.about_company_conveniences).toList()
    )

}