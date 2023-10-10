package ru.apteka.main.presentation.main

import android.view.Gravity
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.main.databinding.ToolbarMenuBinding
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
        mActivity.setSupportActionBar(binding.toolbar)
        mActivity.supportActionBar!!.setDisplayShowCustomEnabled(true)
        viewModel.navigationManager.bottomNavBar = binding.bottomNav.component
        viewModel.navigationManager.topLevelDestinationIds = setOf(
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

    override fun onResume() {
        super.onResume()
        mActivity.supportActionBar?.setCustomView(
            DataBindingUtil.inflate<ToolbarMenuBinding>(layoutInflater, R.layout.toolbar_menu, null, false).apply {
                ivMenuSearch.setOnClickListener {

                }
                ivMenuDoctor.setOnClickListener {

                }
                ivMenuAuth.setOnClickListener {

                }
            }.root,
            ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.END
            )
        )
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
                    mActivity.setupActionBarWithNavController(navController, viewModel.navigationManager.getAppBarConfiguration())
                }
            }
    }
}