package ru.apteka.social

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.BottomSheetService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BottomSheet
import ru.apteka.home.presentation.home.HomeFragmentDirections
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.databinding.GeneralNavigationViewBinding
import ru.apteka.social.presentation.auth.AuthActivity
import javax.inject.Inject
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.home.R as HomeR
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.orders.R as OrdersR
import ru.apteka.stocks.R as StocksR


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var messageNoticeService: MessageNoticeService

    @Inject
    lateinit var bottomSheetService: BottomSheetService

    @Inject
    lateinit var accountsPreferences: AccountsPreferences

    @Inject
    lateinit var userPreferences: UserPreferences

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    private val generalNavController by lazy {
        findNavController(R.id.general_nav_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        navigationManager.apply {
            generalNavController = this@MainActivity.generalNavController
        }

        navigationManager.showAppMenu = {
            bottomSheetService.show(
                BottomSheetModel(
                    binding = GeneralNavigationViewBinding.inflate(layoutInflater, null, false)
                        .also { binding ->
                            fun navigate(graphId: Int, bundle: Bundle = bundleOf()) {
                                navigationManager.generalNavController.navigateWithAnim(
                                    graphId,
                                    bundle
                                )
                                bottomSheetService.close()
                            }

                            if (accountsPreferences.account == null) {
                                binding.appMenuItemAuth.icon =
                                    ContextCompat.getDrawable(
                                        this@MainActivity,
                                        R.drawable.ic_login
                                    )
                                binding.appMenuItemAuth.title = getString(R.string.main_menu_login)
                            } else {
                                binding.appMenuItemAuth.icon =
                                    ContextCompat.getDrawable(
                                        this@MainActivity,
                                        R.drawable.ic_account
                                    )
                                binding.appMenuItemAuth.title =
                                    getString(R.string.main_menu_profile)
                            }

                            binding.appMenuItemAuth.item.setOnClickListener {
                                if (accountsPreferences.account == null) {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            AuthActivity::class.java
                                        )
                                    )
                                } else {
                                    fun navigateProfile() {
                                        navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
                                            HomeFragmentDirections.toProfileFragment()
                                        )
                                    }

                                    fun navigateBackToProfile() {
                                        navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(
                                            HomeR.id.homeFragment, false
                                        )
                                        lifecycleScope.launchIO {
                                            delay(100)
                                            launchMain {
                                                navigateProfile()
                                            }
                                        }
                                    }
                                    if (navigationManager.currentBottomNavControllerLiveData.value!!.graph.id == MainCommonR.id.home_graph) {
                                        navigateProfile()
                                    } else {
                                        navigationManager.onSelectItemId(MainCommonR.id.home_graph)
                                        lifecycleScope.launchIO {
                                            delay(100)
                                            if (navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id == HomeR.id.homeFragment) {
                                                launchMain {
                                                    navigateProfile()
                                                }
                                            } else {
                                                if (navigationManager.currentBottomNavControllerLiveData.value!!.currentDestination!!.id != HomeR.id.profileFragment) {
                                                    mainThread {
                                                        navigateBackToProfile()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                bottomSheetService.close()
                            }

                            binding.appMenuItemCity.title = userPreferences.city?.name
                                ?: getString(R.string.main_menu_city_not_selected)
                            binding.appMenuItemCity.item.setOnClickListener {
                                navigate(ru.apteka.choosing_city_api.R.id.choosing_city_graph)
                            }

                            binding.appMenuItemMyOrders.apply {
                                root.visibleIf(accountsPreferences.account != null)
                                count = 10
                                item.setOnClickListener {
                                    //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                                }
                            }

                            binding.appMenuItemNotifications.apply {
                                root.visibleIf(accountsPreferences.account != null)
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
                                navigate(
                                    ru.apteka.pharmacies_map_api.R.id.pharmacies_map_graph,
                                    bundleOf(
                                        PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                                    )
                                )
                            }

                            binding.appMenuItemAboutCompany.item.setOnClickListener {
                                navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                            }

                            binding.appMenuItemWorkWithUs.item.setOnClickListener {
                                navigate(ru.apteka.work_with_us_api.R.id.work_with_us_graph)
                            }

                            binding.appMenuItemCustomers.item.setOnClickListener {
                                //(ru.apteka.contacts_api.R.id.contacts_graph)
                            }

                            binding.appMenuItemSymptomsAndDiseases.item.setOnClickListener {
                                //navigate(ru.apteka.contacts_api.R.id.contacts_graph)
                            }

                            binding.appMenuItemBrands.item.setOnClickListener {
                                navigate(ru.apteka.brands_api.R.id.brands_graph)
                            }

                            binding.appMenuItemCharity.item.setOnClickListener {
                                navigate(ru.apteka.charity_api.R.id.charity_graph)
                            }

                            binding.appMenuItemContacts.item.setOnClickListener {
                                navigate(ru.apteka.contacts_api.R.id.contacts_graph)
                            }

                            binding.appMenuItemLegalDocuments.item.setOnClickListener {
                                //navigate(ru.apteka.contacts_api.R.id.contacts_graph)
                            }

                            binding.appMenuItemRateApp.item.setOnClickListener {
                                //navigate(ru.apteka.contacts_api.R.id.contacts_graph)
                            }


                            binding.appMenuItemReviews.item.setOnClickListener {
                                navigate(ru.apteka.reviews_api.R.id.reviews_graph)
                            }

                            binding.appMenuItemCooperation.item.setOnClickListener {
                                navigate(ru.apteka.cooperation_api.R.id.cooperation_graph)
                            }

                            binding.appMenuItemLicensesAndPermits.item.setOnClickListener {
                                navigate(ru.apteka.licenses_api.R.id.licenses_graph)
                            }

                            binding.appMenuItemPartners.item.setOnClickListener {
                                //navigate()
                            }

                            binding.appMenuItemFaq.item.setOnClickListener {
                                navigate(ru.apteka.faq_api.R.id.faq_graph)
                            }

                        }
                )
            )
        }

        lifecycleScope.launchIO {
            messageNoticeService.commonDialog.collect { dialogModel ->
                mainThread {
                    showCommonDialog(
                        CommonDialogModel(
                            fragmentManager = supportFragmentManager,
                            dialogModel = dialogModel
                        )
                    )
                }
            }
        }

        lifecycleScope.launchIO {
            messageNoticeService.commonToast.collect { message ->
                mainThread {
                    showToast(
                        ToastModel(
                            this@MainActivity,
                            message
                        )
                    )
                }
            }
        }

        lifecycleScope.launchIO {
            bottomSheetService.bottomSheet.collect { bottomSheet ->
                mainThread {
                    bottomSheetService.bottomSheetDialog = BottomSheet.newInstance(
                        bottomSheet
                    ).apply {
                        show(supportFragmentManager, BottomSheet.TAG)
                    }
                }
            }
        }
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.call()
    }

    /**
     * Переопределение popBackStack необходимо в этом случае, если приложение запускается по глубокой ссылке.
     */
    override fun onBackPressed() {
        val currentGeneralDestinationId =
            navigationManager.generalNavController.currentBackStackEntry?.destination?.id
        val currentMainDestinationId =
            navigationManager.currentBottomNavControllerLiveData.value?.currentBackStackEntry?.destination?.id
        if (currentGeneralDestinationId != R.id.mainFragment) {
            navigationManager.generalNavController.popBackStack()
        } else {
            if (currentMainDestinationId == CatalogR.id.catalogFragment
                || currentMainDestinationId == OrdersR.id.ordersFragment
                || currentMainDestinationId == StocksR.id.stocksFragment
                || currentMainDestinationId == BasketR.id.basketFragment
            ) {
                navigationManager.onSelectItemId(MainCommonR.id.home_graph)
            } else {
                if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                    super.onBackPressed()
                }
            }
        }

    }
}