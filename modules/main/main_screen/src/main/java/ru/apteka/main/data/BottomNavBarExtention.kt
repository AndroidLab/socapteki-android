package ru.apteka.main.data

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.apteka.components.data.models.BottomAppBarModel

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

    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        val graphId = navHostFragment.navController.graph.id

        if (index == 0) {
            firstFragmentGraphId = graphId
        }

        graphIdToTagMap[graphId] = fragmentTag
        if (bottomAppBarModel.selectedItemId.value!! == graphId) {
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    var selectedFragmentTag = graphIdToTagMap[bottomAppBarModel.selectedItemId.value!!]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedFragmentTag == firstFragmentTag

    bottomAppBarModel.setOnItemSelectedListener { itemId ->
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newSelectedItemTag = graphIdToTagMap[itemId]
            if (selectedFragmentTag == newSelectedItemTag) {
                false
            } else {
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val newSelectedFragment = fragmentManager.findFragmentByTag(newSelectedItemTag)
                        as NavHostFragment

                if (firstFragmentTag != newSelectedItemTag) {
                    fragmentManager.commit {
                        attach(newSelectedFragment)
                        setPrimaryNavigationFragment(newSelectedFragment)

                        graphIdToTagMap.forEach { (_, fragmentTag) ->
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

   // setupItemReselected(graphIdToTagMap, fragmentManager)

    //setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag!!)) {
            bottomAppBarModel.onItemSelected(firstFragmentGraphId)
        }

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

        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )
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

        navController.popBackStack(
            navController.graph.startDestinationId, false
        )
    }
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.commit {
        detach(navHostFragment)
    }
}


private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.commit {
        attach(navHostFragment)
        apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
    }
}


private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    (fragmentManager.findFragmentByTag(fragmentTag) as? NavHostFragment)?.let {
        return it
    }

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