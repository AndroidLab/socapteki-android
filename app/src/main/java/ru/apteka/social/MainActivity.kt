package ru.apteka.social

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.BottomSheetService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.ui.BottomSheet
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.presentation.auth.AuthActivity
import javax.inject.Inject
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.stocks.R as StocksR
import ru.apteka.menu.R as MenuR


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
            onAuthNavigate = {
                startActivity(
                    Intent(
                        this@MainActivity,
                        AuthActivity::class.java
                    )
                )
            }
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
        val previousMainDestinationId =
            navigationManager.currentBottomNavControllerLiveData.value?.previousBackStackEntry?.destination?.id
        if (currentGeneralDestinationId != R.id.mainFragment) {
            navigationManager.generalNavController.popBackStack()
        } else {
            if (currentMainDestinationId == CatalogR.id.catalogFragment
                || currentMainDestinationId == StocksR.id.stocksFragment
                || currentMainDestinationId == BasketR.id.basketFragment
                || previousMainDestinationId == MenuR.id.menuFragment
            ) {
                if (previousMainDestinationId == MenuR.id.menuFragment) {
                    navigationManager.currentBottomNavControllerLiveData.value?.popBackStack()
                }
                navigationManager.onSelectItemId(MainCommonR.id.home_graph)
            } else {
                if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                    super.onBackPressed()
                }
            }
        }

    }
}