package ru.apteka.main.presentation.main

import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import ru.apteka.catalog.presentation.catalog.CatalogFragmentDirections
import ru.apteka.catalog.presentation.catalog_products.CatalogProductsFragment
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.setImageTint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.components.R as ComponentsR
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
        binding.bottomAppBarModel = viewModel.bottomAppBar

        viewModel.navigationManager.topLevelMainDestinationIds = setOf(
            HomeR.id.homeFragment,
            CatalogR.id.catalogFragment,
            OrdersR.id.ordersFragment,
            FavoritesR.id.favoritesFragment,
            BasketR.id.basketFragment,
        )

        binding.mainFab.setOnClickListener {
            val controller = viewModel.navigationManager.currentBottomNavControllerLiveData.value!!
            if (controller.graph.id == R.id.home_graph) {
                if (controller.currentDestination!!.id == HomeR.id.bonusProgramFragment) {
                    controller.popBackStack()
                    binding.mainFab.apply {
                        setImageResource(R.drawable.ic_home)
                        setImageTint(
                            ContextCompat.getColor(
                                requireContext(),
                                ComponentsR.color.color_primary
                            )
                        )
                    }
                } else {
                    val navOptions: NavOptions = NavOptions.Builder()
                        .setEnterAnim(ru.apteka.home.R.anim.flip_enter_anim)
                        .setExitAnim(ru.apteka.home.R.anim.flip_exit_anim)
                        .setPopEnterAnim(ru.apteka.home.R.anim.flip_pop_enter_anim)
                        .setPopExitAnim(ru.apteka.home.R.anim.flip_pop_exit_anim)
                        .build()
                    controller.navigate(
                        HomeR.id.bonusProgramFragment,
                        bundleOf(),
                        navOptions
                    )
                    binding.mainFab.apply {
                        setImageResource(R.drawable.ic_card)
                        setImageTint(
                            ContextCompat.getColor(
                                requireContext(),
                                ComponentsR.color.color_primary
                            )
                        )
                    }
                }
            } else {
                viewModel.bottomAppBar.onSelectItemId(R.id.home_graph)
            }
        }

        setupBottomNavigationBar()
        viewModel.navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            viewLifecycleOwner
        ) {
            setupBottomNavigationBar()
        }

        viewModel.favoriteService.totalCount.observe(viewLifecycleOwner) { count ->
            binding.nbTab4.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
            )
        }

        viewModel.basketService.totalCount.observe(viewLifecycleOwner) { count ->
            binding.nbTab5.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
            )
        }

        viewModel.navigationManager.showSearchProduct = {
            fun navigateToSearch() {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
                    CatalogFragmentDirections.toCatalogProductsFragment(CatalogProductsFragment.SEARCH_MODE)
                )
            }

            fun navigateBackToProfile() {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(
                    CatalogR.id.catalogFragment, false
                )
                lifecycleScope.launchIO {
                    delay(100)
                    launchMain {
                        navigateToSearch()
                    }
                }
            }
            if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.graph.id == R.id.catalog_graph) {
                navigateToSearch()
            } else {
                viewModel.navigationManager.onSelectItemId(R.id.catalog_graph)
                lifecycleScope.launchIO {
                    delay(100)
                    if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id == CatalogR.id.catalogFragment) {
                        launchMain {
                            navigateToSearch()
                        }
                    } else {
                        if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id != CatalogR.id.catalogProductsFragment) {
                            mainThread {
                                navigateBackToProfile()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupBottomNavigationBar() {
        viewModel.navigationManager.currentBottomNavControllerLiveData =
            binding.bottomAppBar.setupWithNavController(
                bottomAppBarModel = viewModel.bottomAppBar,
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