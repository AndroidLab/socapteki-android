package ru.apteka.main.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.navigation_manager.NavigationManager
import ru.apteka.components.data.services.BasketService
import javax.inject.Inject


/**
 * Представляет модель представления "Основной экран".
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    val basketService: BasketService
): ViewModel() {



}