package ru.apteka.new_module.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.ui.BaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "".
 */
@HiltViewModel
class PartnersViewModel @Inject constructor(
    private val requestHandler: RequestHandler
) : BaseViewModel() {


    init {

    }

}