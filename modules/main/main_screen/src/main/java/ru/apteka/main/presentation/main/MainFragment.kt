package ru.apteka.main.presentation.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.catalog.presentation.catalog.CatalogFragmentDirections
import ru.apteka.catalog.presentation.catalog_products.CatalogProductsFragment
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_CATALOG
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_HOME
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.setImageTint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.main.R
import ru.apteka.main.data.CircleEdgeTreatment
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MainFragmentBinding
import ru.apteka.main.databinding.MenuNavigationViewBinding
import ru.apteka.main.databinding.RateDialogBinding
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

        viewModel.navigationManager.onSelectItemMenu = { graphId, bundle ->
            navigate(graphId, bundle)
        }

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
                            binding.account = viewModel.accountsPreferences.account

                            binding.appMenuItemProfile.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.profile_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCity.title = viewModel.userPreferences.city?.name
                                ?: getString(R.string.menu_city_not_selected)
                            binding.appMenuItemCity.item.setOnClickListener {
                                viewModel.navigationManager.generalNavController.navigateWithAnim(
                                    ru.apteka.choosing_city_api.R.id.choosing_city_graph,
                                )
                                viewModel.bottomSheetService.close()
                            }

                            binding.appMenuItemMyOrders.apply {
                                count = 10
                                item.setOnClickListener {
                                    viewModel.navigationManager.generalNavController.navigateWithAnim(
                                        ru.apteka.orders_api.R.id.orders_graph,
                                    )
                                    viewModel.bottomSheetService.close()
                                }
                            }

                            binding.appMenuItemNotifications.apply {
                                count = 33
                                item.setOnClickListener {
                                    viewModel.navigationManager.onSelectItemMenu(
                                        MainCommonR.id.notifications_graph,
                                        bundleOf()
                                    )
                                }
                            }

                            binding.appMenuItemLoyaltyProgram.item.setOnClickListener {
                                //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                            }

                            binding.appMenuItemReferralProgram.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.referral_program_graph,
                                    bundleOf()
                                )
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
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.about_company_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemWorkWithUs.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.work_with_us_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCustomers.item.setOnClickListener {
                                //navigate()
                            }

                            binding.appMenuItemSymptomsAndDiseases.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.symptoms_diseases_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemBrands.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.brands_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCharity.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.charity_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemContacts.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.contacts_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemLegalDocuments.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    MainCommonR.id.licenses_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemRateApp.item.setOnClickListener {
                                showCommonDialog(
                                    CommonDialogModel(
                                        fragmentManager = parentFragmentManager,
                                        dialogModel = DialogModel(
                                            bodyContent = BodyContentModel(
                                                layoutId = R.layout.rate_dialog
                                            ) { dialog, binding -> },
                                            buttonCancel = DialogButtonModel(
                                                text = R.string.rate_app_cancel
                                            ),
                                            buttonConfirm = DialogButtonModel(
                                                text = R.string.rate_app_confirm
                                            ) {
                                                val uri: Uri =
                                                    Uri.parse("market://details?id=${it.applicationContext.packageName}")
                                                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                                                goToMarket.addFlags(
                                                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                                                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                                )
                                                try {
                                                    startActivity(goToMarket)
                                                } catch (e: ActivityNotFoundException) {
                                                    startActivity(
                                                        Intent(
                                                            Intent.ACTION_VIEW,
                                                            Uri.parse("http://play.google.com/store/apps/details?id=${it.applicationContext.packageName}")
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    )
                                )
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

                            binding.mbMenuAuth.setOnClickListener {
                                viewModel.navigationManager.onAuthNavigate()
                            }
                        },
                    useScrollableContainer = false
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

    private fun navigate(graphId: Int, bundle: Bundle) {
        if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.parent!!.id != graphId) {
            viewModel.navigationManager.onSelectItemId(viewModel.bottomAppBar.item_5.itemId)
            lifecycleScope.launchMain {
                delay(100)
                if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.id != MenuR.id.menuFragment) {
                    viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(
                        MenuR.id.menuFragment,
                        false
                    )
                }
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
                    graphId, bundle
                )
            }
        }
        viewModel.bottomSheetService.close()
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