package ru.apteka.components.data.navigation_manager

import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.apteka.common.data.utils.single_live_event.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates


/**
 * Представляет менеджер навигации.
 */
@Singleton
class NavigationManager @Inject constructor() {

    /**
     * Возвращает или устанавливает идентификаторы пунктов назначения основного экрана.
     */
    var topLevelDestinationIds: Set<Int> by Delegates.notNull()

    /**
     * Возвращает или устанавливает drawer.
     */
    var drawerLayout: DrawerLayout by Delegates.notNull()

    /**
     * Устанавливает или возвращает главный навигационный контроллер.
     */
    var generalNavController: NavController by Delegates.notNull()

    /**
     * Устанавливает или возвращает нижний бар навигации.
     */
    var bottomNavBar: BottomNavigationView by Delegates.notNull()

    /**
     * Возвращает или устанавливает навигационный контроллер для нижней панели навигации.
     */
    var currentBottomNavControllerLiveData: LiveData<NavController> by Delegates.notNull()

    /**
     * Возвращает событие необходимости установить конфигурацию для нижнего навигационного бара.
     */
    val isBottomNavigationBarNeedUpdateSingleEvent = SingleLiveEvent<Unit>()

    /**
     * Возвращает кофигурацию appbar (Должно быть методом для возвращения нового экземпляра при смене конфигурации).
     */
    fun getAppBarConfiguration() = AppBarConfiguration(
            topLevelDestinationIds,
            drawerLayout
        )

}