package ru.apteka.social

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.barcode_scan.BarCodeScanService
import ru.apteka.components.data.services.bottom_sheet_service.BottomSheetService
import ru.apteka.components.data.services.error_notice_service.ErrorNoticeService
import ru.apteka.components.data.services.error_notice_service.models.IRequestError
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
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
import ru.apteka.components.ui.BottomSheet
import ru.apteka.home.presentation.home.HomeFragmentDirections
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.databinding.GeneralNavigationViewBinding
import ru.apteka.social.presentation.auth.AuthActivity
import ru.apteka.social.presentation.scan.ScanActivity
import javax.inject.Inject
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.favorites.R as FavoritesR
import ru.apteka.home.R as HomeR
import ru.apteka.main.R as MainR
import ru.apteka.orders.R as OrdersR


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var errorNoticeService: ErrorNoticeService

    @Inject
    lateinit var messageNoticeService: MessageNoticeService

    @Inject
    lateinit var bottomSheetService: BottomSheetService

    @Inject
    lateinit var barCodeScanService: BarCodeScanService

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
                GeneralNavigationViewBinding.inflate(layoutInflater, null, false).also { binding ->
                    fun navigate(graphId: Int) {
                        navigationManager.generalNavController.navigateWithAnim(graphId)
                    }

                    if (accountsPreferences.account == null) {
                        binding.appMenuItemAuth.icon =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_login)
                        binding.appMenuItemAuth.title = getString(R.string.main_menu_login)
                    } else {
                        binding.appMenuItemAuth.icon =
                            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_account)
                        binding.appMenuItemAuth.title = getString(R.string.main_menu_profile)
                    }

                    binding.appMenuItemAuth.item.setOnClickListener {
                        if (accountsPreferences.account == null) {
                            startActivity(Intent(this@MainActivity, AuthActivity::class.java))
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
                            if (navigationManager.currentBottomNavControllerLiveData.value!!.graph.id == MainR.id.home_graph) {
                                navigateProfile()
                            } else {
                                navigationManager.onSelectItemId(MainR.id.home_graph)
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
                        navigationManager.generalNavController.navigateWithAnim(
                            ru.apteka.choosing_city_api.R.id.choosing_city_graph,
                        )
                        bottomSheetService.close()
                    }

                    binding.appMenuItemAboutCompany.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemWorkWithUs.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemManufacturers.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemContacts.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemPharmacies.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemReviews.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemCooperation.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemLicensesAndPermits.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemPartners.item.setOnClickListener {
                        //navigate()
                    }

                    binding.appMenuItemBrands.item.setOnClickListener {
                        //navigate()
                    }

                }
            )

        }

        lifecycleScope.launchIO {
            errorNoticeService.error.collect {
                showCommonDialog(
                    CommonDialogModel(
                        fragmentManager = supportFragmentManager,
                        dialogModel = DialogModel(
                            title = it.title,
                            message = MessageModel(
                                message = when (it) {
                                    is IRequestError.RequestErrorResMsg -> it.msg
                                    is IRequestError.RequestErrorStringMsg -> it.msg
                                }
                            )
                        )
                    )
                )
            }
        }

        lifecycleScope.launchIO {
            messageNoticeService.commonDialog.collect { dialogModel ->
                showCommonDialog(
                    CommonDialogModel(
                        fragmentManager = supportFragmentManager,
                        dialogModel = dialogModel
                    )
                )
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
            bottomSheetService.bottomSheet.collect { bottomSheetBinding ->
                mainThread {
                    bottomSheetService.bottomSheetDialog = BottomSheet.newInstance(
                        BottomSheet.BottomSheetModel(
                            bottomSheetBinding
                        )
                    ).apply {
                        show(supportFragmentManager, BottomSheet.TAG)
                    }
                }
            }
        }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data!!.extras!!.getString(ScanActivity.BARCODE_SCAN)!!
                    barCodeScanService.sendBarcodeResult(data)
                }
            }

        lifecycleScope.launchIO {
            barCodeScanService.scanStart.collect {
                mainThread {
                    resultLauncher.launch(
                        Intent(this@MainActivity, ScanActivity::class.java)
                    )
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
                || currentMainDestinationId == FavoritesR.id.favoritesFragment
                || currentMainDestinationId == BasketR.id.basketFragment
            ) {
                navigationManager.onSelectItemId(MainR.id.home_graph)
            } else {
                if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                    super.onBackPressed()
                }
            }
        }

    }
}