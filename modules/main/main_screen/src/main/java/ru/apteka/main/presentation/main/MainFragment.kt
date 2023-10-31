package ru.apteka.main.presentation.main

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.favorites.R as FavoritesR
import ru.apteka.home.R as HomeR
import ru.apteka.orders.R as OrdersR


/**
 * Представляет фрагмент "Основной экран".
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutId: Int = R.layout.main_fragment

    override fun onViewBindingInflated(binding: MainFragmentBinding) {
        viewModel.navigationManager.bottomNavBar = binding.bottomNav.component
        viewModel.navigationManager.selectedMainDestinationId?.let { destinationId ->
            viewModel.navigationManager.bottomNavBar.selectedItemId = destinationId
            viewModel.navigationManager.selectedMainDestinationId = null
        }
        viewModel.navigationManager.topLevelMainDestinationIds = setOf(
            HomeR.id.homeFragment,
            CatalogR.id.catalogFragment,
            OrdersR.id.ordersFragment,
            FavoritesR.id.favoritesFragment,
            BasketR.id.basketFragment,
        )
        setupBottomNavigationBar()
        viewModel.navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            viewLifecycleOwner
        ) {
            setupBottomNavigationBar()
        }

        viewModel.favoriteService.totalCount.observe(viewLifecycleOwner) { count ->
            binding.bottomNav.nbTab4.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
            )
        }

        viewModel.basketService.totalCount.observe(viewLifecycleOwner) { count ->
            binding.bottomNav.nbTab5.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
            )
        }
    }

    private fun setupBottomNavigationBar() {
        viewModel.navigationManager.currentBottomNavControllerLiveData =
            binding.bottomNav.component.setupWithNavController(
                navGraphIds = listOf(
                    HomeR.navigation.home_graph,
                    CatalogR.navigation.catalog_graph,
                    OrdersR.navigation.orders_graph,
                    FavoritesR.navigation.favorites_graph,
                    BasketR.navigation.basket_graph
                ),
                fragmentManager = childFragmentManager,
                containerId = R.id.nav_host_container,
                intent = requireActivity().intent,
            )
    }

}