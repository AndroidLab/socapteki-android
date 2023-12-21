package ru.apteka.main.presentation.main

import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.catalog.presentation.catalog.CatalogFragmentDirections
import ru.apteka.catalog.presentation.catalog_products.CatalogProductsFragment
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_CATALOG
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_HOME
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.databinding.MainFragmentBinding
import javax.inject.Inject
import ru.apteka.catalog.R as CatalogR
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Основной экран".
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<Nothing, MainFragmentBinding>() {
    override val layoutId: Int = R.layout.main_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: MainFragmentBinding) {

        setFragmentResultListener(NAVIGATE_REQUEST_KEY_TO_CATALOG) { _, _ ->
            navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.catalog_graph)
        }
        setFragmentResultListener(NAVIGATE_REQUEST_KEY_TO_HOME) { _, _ ->
            navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.home_graph)
        }



        navigationManager.showSearchProduct = {
            fun navigateToSearch() {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
                    CatalogFragmentDirections.toCatalogProductsFragment(CatalogProductsFragment.SEARCH_MODE)
                )
            }

            fun navigateBackToProfile() {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(
                    CatalogR.id.catalogFragment, false
                )
                lifecycleScope.launchIO {
                    delay(100)
                    launchMain {
                        navigateToSearch()
                    }
                }
            }
            if (navigationManager.currentBottomNavControllerLiveData.value!!.graph.id == ComponentsR.id.catalog_graph) {
                navigateToSearch()
            } else {
                navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.catalog_graph)
                lifecycleScope.launchIO {
                    delay(100)
                    if (navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id == CatalogR.id.catalogFragment) {
                        launchMain {
                            navigateToSearch()
                        }
                    } else {
                        if (navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id != CatalogR.id.catalogProductsFragment) {
                            mainThread {
                                navigateBackToProfile()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.call()
    }

    override fun onResume() {
        super.onResume()

    }
}