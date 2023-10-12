package ru.apteka.about_company.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.common.data.RequestHandler
import ru.apteka.common.ui.BaseViewModel
import ru.apteka.components.data.navigation_manager.INavigationManager
import javax.inject.Inject


/**
 * Представляет модель представления "".
 */
@HiltViewModel
class AboutCompanyViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    val navigationManager: INavigationManager
) : BaseViewModel() {


    init {

    }

}