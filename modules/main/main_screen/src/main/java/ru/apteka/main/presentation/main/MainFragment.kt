package ru.apteka.main.presentation.main

import android.view.Gravity
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.main.databinding.ToolbarMenuBinding
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.favorites.R as FavoritesR
import ru.apteka.home.R as HomeR
import ru.apteka.orders.R as OrdersR
import ru.apteka.components.R as ComponentsR
import ru.apteka.order_search_api.R as OrderSearchApiR


/**
 * Представляет фрагмент "Основной экран".
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutId: Int = R.layout.main_fragment

    override fun onViewBindingInflated(binding: MainFragmentBinding) {
        viewModel.navigationManager.bottomNavBar = binding.bottomNav.component
        viewModel.navigationManager.topLevelMainDestinationIds = setOf(
            HomeR.id.homeFragment,
            CatalogR.id.catalogFragment,
            OrdersR.id.ordersFragment,
            FavoritesR.id.favoritesFragment,
            BasketR.id.basketFragment,
        )
        viewModel.navigationManager.onBottomNavBarRestore.observe(viewLifecycleOwner) { itemId ->
            binding.bottomNav.component.selectedItemId = itemId!!
        }
        setupBottomNavigationBar()
        viewModel.navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            viewLifecycleOwner
        ) {
            setupBottomNavigationBar()
        }

        viewModel.basketService.products.observe(viewLifecycleOwner) { products ->
            products.size.toUInt().also { productCount ->
                binding.bottomNav.nbTab5.setNumber(
                    if (productCount > 0u) {
                        productCount
                    } else {
                        null
                    }
                )
            }
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
                intent = requireActivity().intent
            ).apply {
                observe(viewLifecycleOwner) { navController ->
                    mActivity.setupActionBarWithNavController(
                        navController,
                        viewModel.navigationManager.getAppBarConfiguration()
                    )
                }
            }
    }

    override fun onResume() {
        super.onResume()
        mActivity.supportActionBar?.setCustomView(
            DataBindingUtil.inflate<ToolbarMenuBinding>(
                layoutInflater,
                R.layout.toolbar_menu,
                null,
                false
            ).apply {
                ivMenuSearch.setOnClickListener {
                    when (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id) {
                        HomeR.id.homeFragment -> {}
                        CatalogR.id.catalogFragment -> {}
                        OrdersR.id.ordersFragment -> {
                            viewModel.navigationManager.generalNavController.navigate(
                                OrderSearchApiR.id.order_search_graph
                            )
                        }
                        FavoritesR.id.favoritesFragment -> {}
                        BasketR.id.basketFragment -> {}
                    }
                }
                ivMenuDoctor.setOnClickListener {

                }
                ivMenuAuth.setOnClickListener {
                    viewModel.navigationManager.navigateToAuthActivity()
                }
            }.root,
            ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.END
            )
        )
    }

}