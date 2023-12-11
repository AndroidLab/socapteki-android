package ru.apteka.main.presentation.main

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import ru.apteka.catalog.presentation.catalog.CatalogFragmentDirections
import ru.apteka.catalog.presentation.catalog_products.CatalogProductsFragment
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_CATALOG
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_HOME
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.setImageTint
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.CircleEdgeTreatment
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.main.databinding.MenuNavigationViewBinding
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.components.R as ComponentsR
import ru.apteka.home.R as HomeR
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.menu.R as MenuR
import ru.apteka.stocks.R as StocksR


/**
 * Представляет фрагмент "Основной экран".
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutId: Int = R.layout.main_fragment

    override fun onViewBindingInflated(binding: MainFragmentBinding) {
        binding.bottomAppBarModel = viewModel.bottomAppBar
        setFragmentResultListener(NAVIGATE_REQUEST_KEY_TO_CATALOG) { _, _ ->
            viewModel.navigationManager.onSelectItemId(ru.apteka.main_common.R.id.catalog_graph)
        }
        setFragmentResultListener(NAVIGATE_REQUEST_KEY_TO_HOME) { _, _ ->
            viewModel.navigationManager.onSelectItemId(ru.apteka.main_common.R.id.home_graph)
        }

        binding.bottomAppBar.apply {
            background = MaterialShapeDrawable(
                (background as MaterialShapeDrawable).shapeAppearanceModel
                    .toBuilder()
                    .setTopRightCorner(CornerFamily.ROUNDED, 12.dp.toFloat())
                    .setTopLeftCorner(CornerFamily.ROUNDED, 12.dp.toFloat())
                    .setTopEdge(CircleEdgeTreatment(36.dp.toFloat()))
                    .build()
            ).apply {
                elevation = 8.dp.toFloat()
                shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
                setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        ru.apteka.components.R.color.white
                    )
                )
                paintStyle = Paint.Style.FILL
            }
        }

        viewModel.navigationManager.topLevelMainDestinationIds = setOf(
            HomeR.id.homeFragment,
            CatalogR.id.catalogFragment,
            StocksR.id.stocksFragment,
            BasketR.id.basketFragment,
            MenuR.id.menuFragment
        )

        binding.mainFab.setOnClickListener {
            val controller = viewModel.navigationManager.currentBottomNavControllerLiveData.value!!
            if (controller.graph.id == MainCommonR.id.home_graph) {
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
                viewModel.bottomAppBar.onItemSelected(MainCommonR.id.home_graph)
            }
        }

        binding.bottomNavigationCatalogItem.item.setOnClickListener {
            viewModel.bottomAppBar.apply {
                onItemSelected(item_2.itemId)
            }
        }

        binding.bottomNavigationStocksItem.item.setOnClickListener {
            viewModel.bottomAppBar.apply {
                onItemSelected(item_3.itemId)
            }
        }

        binding.bottomNavigationBasketItem.item.setOnClickListener {
            viewModel.bottomAppBar.apply {
                onItemSelected(item_4.itemId)
            }
        }

        binding.bottomNavigationMenuItem.item.setOnClickListener {
            viewModel.bottomSheetService.show(
                BottomSheetModel(
                    binding = MenuNavigationViewBinding.inflate(layoutInflater, null, false)
                        .also { binding ->
                            fun navigate(graphId: Int, bundle: Bundle = bundleOf()) {
                                if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.parent!!.id != graphId) {
                                    viewModel.navigationManager.onSelectItemId(viewModel.bottomAppBar.item_5.itemId)
                                    lifecycleScope.launchMain {
                                        delay(100)
                                        if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.id != MenuR.id.menuFragment) {
                                            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(MenuR.id.menuFragment, false)
                                        }
                                        viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
                                            graphId, bundle
                                        )
                                    }
                                }
                                viewModel.bottomSheetService.close()
                            }

                            if (viewModel.accountsPreferences.account == null) {
                                binding.appMenuItemAuth.icon =
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_menu_login
                                    )
                                binding.appMenuItemAuth.title = getString(R.string.menu_login)
                            } else {
                                binding.appMenuItemAuth.icon =
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_menu_account
                                    )
                                binding.appMenuItemAuth.title =
                                    getString(R.string.menu_profile)
                            }

                            binding.appMenuItemAuth.item.setOnClickListener {
                                if (viewModel.accountsPreferences.account == null) {
                                    viewModel.navigationManager.onAuthNavigate()
                                } else {
                                    navigate(MainCommonR.id.profile_graph)
                                }
                                viewModel.bottomSheetService.close()
                            }

                            binding.appMenuItemCity.title = viewModel.userPreferences.city?.name
                                ?: getString(R.string.menu_city_not_selected)
                            binding.appMenuItemCity.item.setOnClickListener {
                                //navigate(ru.apteka.choosing_city_api.R.id.choosing_city_graph)
                            }

                            binding.appMenuItemMyOrders.apply {
                                root.visibleIf(viewModel.accountsPreferences.account != null)
                                count = 10
                                item.setOnClickListener {
                                    //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                                }
                            }

                            binding.appMenuItemNotifications.apply {
                                root.visibleIf(viewModel.accountsPreferences.account != null)
                                count = 33
                                item.setOnClickListener {
                                    //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                                }
                            }

                            binding.appMenuItemLoyaltyProgram.item.setOnClickListener {
                                //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                            }

                            binding.appMenuItemReferralProgram.item.setOnClickListener {
                                //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                            }

                            binding.appMenuItemPharmacies.item.setOnClickListener {
                                viewModel.navigationManager.generalNavController.navigateWithAnim(
                                    ru.apteka.pharmacies_map_api.R.id.pharmacies_map_graph,
                                    bundleOf(
                                        PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                                    )
                                )
                                viewModel.bottomSheetService.close()
                            }

                            binding.appMenuItemAboutCompany.item.setOnClickListener {
                                navigate(MainCommonR.id.about_company_graph)
                            }

                            binding.appMenuItemWorkWithUs.item.setOnClickListener {
                                navigate(MainCommonR.id.work_with_us_graph)
                            }

                            binding.appMenuItemCustomers.item.setOnClickListener {
                                //navigate()
                            }

                            binding.appMenuItemSymptomsAndDiseases.item.setOnClickListener {
                                //navigate()
                            }

                            binding.appMenuItemBrands.item.setOnClickListener {
                                navigate(MainCommonR.id.brands_graph)
                            }

                            binding.appMenuItemCharity.item.setOnClickListener {
                                navigate(MainCommonR.id.charity_graph)
                            }

                            binding.appMenuItemContacts.item.setOnClickListener {
                                navigate(MainCommonR.id.contacts_graph)
                            }

                            binding.appMenuItemLegalDocuments.item.setOnClickListener {
                                navigate(MainCommonR.id.licenses_graph)
                            }

                            binding.appMenuItemRateApp.item.setOnClickListener {
                                //navigate()
                            }


                            binding.appMenuItemReviews.item.setOnClickListener {
                                //navigate(ru.apteka.reviews_api.R.id.reviews_graph)
                            }

                            binding.appMenuItemCooperation.item.setOnClickListener {
                                //navigate(ru.apteka.cooperation_api.R.id.cooperation_graph)
                            }

                            binding.appMenuItemPartners.item.setOnClickListener {
                                //navigate()
                            }

                            binding.appMenuItemFaq.item.setOnClickListener {
                                //navigate(ru.apteka.faq_api.R.id.faq_graph)
                            }

                        }
                )
            )
        }

        setupBottomNavigationBar()
        viewModel.navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            viewLifecycleOwner
        ) {
            setupBottomNavigationBar()
        }

        viewModel.basketService.totalCount.observe(viewLifecycleOwner) { count ->
            binding.nbTab4.setNumber(
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
            if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.graph.id == MainCommonR.id.catalog_graph) {
                navigateToSearch()
            } else {
                viewModel.navigationManager.onSelectItemId(MainCommonR.id.catalog_graph)
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
                    StocksR.navigation.stocks_graph,
                    BasketR.navigation.basket_graph,
                    MenuR.navigation.menu_graph,
                ),
                fragmentManager = childFragmentManager,
                containerId = R.id.nav_host_container,
                intent = requireActivity().intent,
            )
    }

}