package ru.apteka.components.data.navigation_manager

import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import ru.apteka.common.data.utils.single_live_event.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет менеджер навигации.
 */
@Singleton
class NavigationManager @Inject constructor() {
    /**
     * Устанавливает или возвращает главный навигационный контроллер.
     */
    lateinit var generalNavController: NavController

    /**
     * Возвращает или устанавливает навигационный контроллер для нижней панели навигации.
     */
    lateinit var currentBottomNavControllerLiveData: LiveData<NavController>

    /**
     * Возвращает событие необходимости установить конфигурацию для нижнего навигационного бара.
     */
    val isBottomNavigationBarNeedUpdateSingleEvent = SingleLiveEvent<Unit>().apply { call() }
}