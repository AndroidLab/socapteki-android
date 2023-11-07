package ru.apteka.main.data

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Управляет различными графами, необходимыми для [BottomNavigationView].
 * @param navGraphIds: Идентификаторы графов.
 * @param fragmentManager Менеджер фрагментов.
 * @param containerId Идентификатор контейнера.
 * @param intent Intent.
 */
fun BottomAppBar.setupWithNavController(
    bottomAppBarModel: BottomAppBarModel,
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent,
): LiveData<NavController> {
    val graphIdToTagMap = HashMap<Int, String>()
    val selectedNavController = MutableLiveData<NavController>()
    var firstFragmentGraphId = 0

    // Сначала создайте NavHostFragment для каждого идентификатора NavGraph
    //Перебирает все графы навигации.
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Возвращает NavHostFragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        // Возвращает идентификатор графа
        val graphId = navHostFragment.navController.graph.id

        if (index == 0) {
            firstFragmentGraphId = graphId
        }

        // Сохраняет таг в карту
        graphIdToTagMap[graphId] = fragmentTag

        //Если выбрана вкладка с текущим графом
        if (bottomAppBarModel.selectedItemId.value!! == graphId) {
            // Обновляет текущий выбранный контроллер навигации.
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Теперь соедините выбор элемента с заменой фрагментов
    var selectedFragmentTag = graphIdToTagMap[bottomAppBarModel.selectedItemId.value!!]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedFragmentTag == firstFragmentTag

    // Слушатель клика по табам
    bottomAppBarModel.setOnItemSelectedListener { itemId ->
        //Если состояние state уже сохранено
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newSelectedItemTag = graphIdToTagMap[itemId]
            //Если выбранный тэг не равен новому тэгу
            if (selectedFragmentTag == newSelectedItemTag) {
                false
            } else {
                //Вставьте все, что находится над первым фрагментом ("фиксированный начальный пункт назначения").
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                /**
                 * Возвращает фрагмент для текущей вкладки.
                 */
                val newSelectedFragment = fragmentManager.findFragmentByTag(newSelectedItemTag)
                        as NavHostFragment

                // Если это не первый тег фрагмента, потому что он всегда находится в заднем стеке.
                if (firstFragmentTag != newSelectedItemTag) {
                    // Выполняет транзакцию, которая очищает back stack и добавляет первый фрагмент
                    // к нему, создавая фиксированный начатый пункт назначения.
                    fragmentManager.commit {
                        attach(newSelectedFragment)
                        setPrimaryNavigationFragment(newSelectedFragment)

                        // Отсоедините все остальные фрагменты.
                        graphIdToTagMap.forEach { _, fragmentTag ->
                            if (fragmentTag != newSelectedItemTag) {
                                detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                            }
                        }
                        addToBackStack(firstFragmentTag)
                        /*.setCustomAnimations(
                            R.anim.slide_to_right,
                            R.anim.slide_center_to_right,
                            R.anim.slide_left_to_right,
                            R.anim.slide_out_right)*/
                        setReorderingAllowed(true)
                    }
                }
                selectedFragmentTag = newSelectedItemTag
                isOnFirstFragment = selectedFragmentTag == firstFragmentTag
                selectedNavController.value = newSelectedFragment.navController
                true
            }
        }
    }

    // Необязательно: при выборе элемента переместите стек обратно в пункт назначения графика
   // setupItemReselected(graphIdToTagMap, fragmentManager)

    // Handle deep link
    //setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Наконец, убедитесь, что мы обновляем наш нижний навигационный вид при изменении заднего стека
    fragmentManager.addOnBackStackChangedListener {
        //Если это не первый фрагмент и это не первый экран домашней вкладки
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag!!)) {
            bottomAppBarModel.onSelectItemId(firstFragmentGraphId)
        }

        // Сброс графика, если текущее назначение недействительно (происходит, когда
        // после использования кнопки "Назад" открывается стек "Назад //").
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent)) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: HashMap<Int, String>,
    fragmentManager: FragmentManager
) {
    setOnItemReselectedListener { menuItem ->
        val newlySelectedItemTag = graphIdToTagMap[menuItem.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestinationId, false
        )
    }
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.commitNow {
        detach(navHostFragment)
    }
}

/**
 * Прикрепляет navHostFragment.
 * @param fragmentManager Менеджер фрагментов.
 * @param navHostFragment Фрагмент навигации.
 * @param isPrimaryNavFragment Флаг указывающий на главную вкладку.
 */
private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.commitNow {
        attach(navHostFragment)
        apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
    }
}

/**
 * Создает или возвращает [NavHostFragment].
 * @param fragmentManager
 * @param fragmentTag Тег фрагмента [NavHostFragment].
 * @param navGraphId Идентификатор графа.
 * @param containerId Идентификатор контейнера.
 * @return [NavHostFragment]
 */
private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    // Если фрагмент узла навигации существует
    (fragmentManager.findFragmentByTag(fragmentTag) as? NavHostFragment)?.let { return it }

    // Cоздаем фрагмент навигации.
    return NavHostFragment.create(navGraphId).also {
        fragmentManager.beginTransaction()
            .add(containerId, it, fragmentTag)
            .commitNow()
    }
}

private fun FragmentManager.isOnBackStack(fragmentTag: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == fragmentTag) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"