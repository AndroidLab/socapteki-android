package ru.apteka.components.data.navigation_manager

import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.apteka.common.data.utils.single_live_event.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates


/**
 * Представляет менеджер навигации.
 */
@Singleton
class NavigationManager @Inject constructor(): INavigationManager {

    /**
     * Возвращает или устанавливает идентификаторы пунктов назначения основного экрана.
     */
    var topLevelMainDestinationIds: Set<Int> by Delegates.notNull()

    /**
     * Возвращает или устанавливает drawer.
     */
    var drawerLayout: DrawerLayout by Delegates.notNull()

    /**
     * Устанавливает или возвращает главный навигационный контроллер.
     */
    var _generalNavController: NavController by Delegates.notNull()

    /**
     * Возвращает или устанавливает навигационный контроллер для нижней панели навигации.
     */
    var currentBottomNavControllerLiveData: LiveData<NavController> by Delegates.notNull()

    /**
     * Возвращает или устанавливает верхний бар.
     */
    var toolBar: ActionBar by Delegates.notNull()

    /**
     * Возвращает или устанавливает нижний бар навигации.
     */
    var bottomNavBar: BottomNavigationView by Delegates.notNull()


    /**
     * Возвращает или устанавливает выбранную вкладку при переходе на внешние destination, для последующего востановления, при возвращении назад.
     * null если находимся на главном экране.
     */
    var selectedMainDestinationId: Int? = null

    /**
     * Обработчкик восстановления вкладки BottomNavBar, при возвращении к MainFragment от внешних destination. (При переходе к внешней вкладке сбрасывает на главную вкладку)
     */
    val onBottomNavBarRestore = SingleLiveEvent<Int>()


    /**
     * Возвращает событие необходимости установить конфигурацию для нижнего навигационного бара.
     */
    val isBottomNavigationBarNeedUpdateSingleEvent = SingleLiveEvent<Unit>()


    /**
     * Навигирует к экрану авторизации.
     */
    var navigateToAuthActivity: () -> Unit by Delegates.notNull()

    /**
     * Возвращает кофигурацию appbar (Должно быть методом для возвращения нового экземпляра при смене конфигурации).
     */
    fun getAppBarConfiguration() = AppBarConfiguration(
            topLevelMainDestinationIds,
            drawerLayout
        )

    override val generalNavController: NavController
        get() = _generalNavController

    override fun hideToolbar() {
        toolBar.hide()
    }

    override fun showToolbar() {
        toolBar.show()
    }
}