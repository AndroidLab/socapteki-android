package ru.apteka.feature_screen.presentation

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import javax.inject.Inject


/**
 * Представляет фрагмент "Фича экран".
 */
@AndroidEntryPoint
class FeatureFragment : BaseFragment<Nothing, Nothing>() {
    override val layoutId: Int? = null

    @Inject
    lateinit var navigationManager: NavigationManager


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(true)
    }

    override fun onStop() {
        super.onStop()
        navigationManager.onBottomAppBarShowed(false)
    }
}