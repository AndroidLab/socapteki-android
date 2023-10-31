package ru.apteka.components.ui

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference


object BottomNavigationUI {
    /*fun onNavDestinationSelected(
        item: MenuItem,
        navController: NavController
    ): Boolean {
        val resId = item.itemId
        var args: Bundle? = null
        val options: NavOptions
        val optionsBuilder: NavOptions.Builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            optionsBuilder.setPopUpTo(findStartDestination(navController.graph).id, false)
        }
        val navAction = navController.currentDestination!!.getAction(resId)
        if (navAction != null) {
            val navOptions = navAction.navOptions

            // Note : You can Add *setLaunchSingleTop* and *setPopUpTo* from *navOptions* to *builder*
            if (navOptions!!.enterAnim != -1) {
                optionsBuilder.setEnterAnim(navOptions.enterAnim)
            }
            if (navOptions.exitAnim != -1) {
                optionsBuilder.setExitAnim(navOptions.exitAnim)
            }
            if (navOptions.popEnterAnim != -1) {
                optionsBuilder.setPopEnterAnim(navOptions.popEnterAnim)
            }
            if (navOptions.popExitAnim != -1) {
                optionsBuilder.setPopExitAnim(navOptions.popExitAnim)
            }
            val navActionArgs = navAction.defaultArguments
            if (navActionArgs != null) {
                args = Bundle()
                args.putAll(navActionArgs)
            }
        }
        options = optionsBuilder.build()
        return try {
            //TODO provide proper API instead of using Exceptions as Control-Flow.
            navController.navigate(resId, args, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun setupWithNavController(
        bottomNavigationView: BottomNavigationView,
        navController: NavController
    ) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            onNavDestinationSelected(
                item,
                navController
            )
        }
        val weakReference = WeakReference(bottomNavigationView)
        navController.addOnDestinationChangedListener(
            object : NavController.OnDestinationChangedListener {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination, arguments: Bundle?
                ) {
                    val view = weakReference.get()
                    if (view == null) {
                        navController.removeOnDestinationChangedListener(this)
                        return
                    }
                    val menu = view.menu
                    var h = 0
                    val size = menu.size()
                    while (h < size) {
                        val item = menu.getItem(h)
                        if (matchDestination(destination, item.itemId)) {
                            item.isChecked = true
                        }
                        h++
                    }
                }
            })
    }

    fun matchDestination(
        destination: NavDestination,
        @IdRes destId: Int
    ): Boolean {
        var currentDestination: NavDestination? = destination
        while (currentDestination!!.id != destId && currentDestination.parent != null) {
            currentDestination = currentDestination.parent
        }
        return currentDestination.id == destId
    }

    fun matchDestinations(
        destination: NavDestination,
        destinationIds: Set<Int?>
    ): Boolean {
        var currentDestination: NavDestination? = destination
        do {
            if (destinationIds.contains(currentDestination!!.id)) {
                return true
            }
            currentDestination = currentDestination.parent
        } while (currentDestination != null)
        return false
    }

    fun findStartDestination(graph: NavGraph): NavDestination {
        var startDestination: NavDestination = graph
        while (startDestination is NavGraph) {
            val parent = startDestination
            startDestination = parent.findNode(parent.getStartDestination())!!
        }
        return startDestination
    }*/
}