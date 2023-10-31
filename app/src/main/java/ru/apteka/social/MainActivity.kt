package ru.apteka.social

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
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
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BottomSheet
import ru.apteka.components.ui.CommonDialogFragment
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.databinding.GeneralNavigationViewBinding
import ru.apteka.social.presentation.auth.AuthActivity
import javax.inject.Inject
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.favorites.R as FavoritesR
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
            navigateToAuthActivity = {
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
            }
        }

        generalNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id != R.id.mainFragment && navigationManager.bottomNavBar.selectedItemId != MainR.id.home_graph) {
                navigationManager.selectedMainDestinationId =
                    navigationManager.bottomNavBar.selectedItemId
            }
        }

        navigationManager.showAppMenu = {
            bottomSheetService.show(
                GeneralNavigationViewBinding.inflate(layoutInflater, null, false).also { binding ->
                    binding.navView.setupWithNavController(generalNavController)
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
                if (bottomSheetBinding != null) {
                    mainThread {
                        bottomSheetService.bottomSheetDialog = BottomSheet.newInstance(
                            BottomSheet.BottomSheetModel(
                                bottomSheetBinding
                            )
                        ).apply {
                            show(supportFragmentManager, BottomSheet.TAG)
                        }
                        bottomSheetService.reset()
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
                || currentMainDestinationId == FavoritesR.id.favoritesFragment
                || currentMainDestinationId == BasketR.id.basketFragment
            ) {
                navigationManager.bottomNavBar.selectedItemId = MainR.id.home_graph
            } else {
                if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                    super.onBackPressed()
                }
            }
        }

    }
}