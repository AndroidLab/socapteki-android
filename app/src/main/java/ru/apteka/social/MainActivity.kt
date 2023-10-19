package ru.apteka.social

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.error_notice_service.ErrorNoticeService
import ru.apteka.components.data.services.error_notice_service.models.IRequestError
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.ui.CommonDialogFragment
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.launchIO
import ru.apteka.social.databinding.ActivityMainBinding
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

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    private val generalNavController by lazy {
        findNavController(R.id.general_nav_host)
    }

    private val generalAppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.mainFragment
            ),
            binding.drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        navigationManager.apply {
            toolBar = supportActionBar!!
            drawerLayout = binding.drawerLayout
            _generalNavController = this@MainActivity.generalNavController
            navigateToAuthActivity = {
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
            }
        }
        binding.navView.setupWithNavController(generalNavController)

        generalNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.mainFragment) {
                if (navigationManager.selectedMainDestinationId != null) {
                    navigationManager.onBottomNavBarRestore.value =
                        navigationManager.selectedMainDestinationId!!
                    navigationManager.selectedMainDestinationId = null
                }
            } else {
                if (navigationManager.bottomNavBar.selectedItemId != MainR.id.home_graph) {
                    navigationManager.selectedMainDestinationId =
                        navigationManager.bottomNavBar.selectedItemId
                    navigationManager.onBottomNavBarRestore.value = MainR.id.home_graph
                }
                setupActionBarWithNavController(generalNavController, binding.drawerLayout)
            }
        }

        lifecycleScope.launchIO {
            errorNoticeService.error.collect {
                showCommonDialog(
                    CommonDialogModel(
                        fragmentManager = supportFragmentManager,
                        dialogModel = DialogModel(
                            title = it.title,
                            message = CommonDialogFragment.CommonDialogMessage(
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
                if (dialogModel != null) {
                    showCommonDialog(
                        CommonDialogModel(
                            fragmentManager = supportFragmentManager,
                            dialogModel = dialogModel
                        )
                    )
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.call()
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (generalNavController.currentDestination!!.id == R.id.mainFragment) {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateUp(
                navigationManager.getAppBarConfiguration()
            )
        } else {
            generalNavController.navigateUp(generalAppBarConfiguration)
        } || super.onSupportNavigateUp()
    }

    /**
     * Переопределение popBackStack необходимо в этом случае, если приложение запускается по глубокой ссылке.
     */
    override fun onBackPressed() {
        val currentGeneralDestinationId =
            navigationManager.generalNavController.currentBackStackEntry?.destination?.id
        val currentMainDestinationId =
            navigationManager.currentBottomNavControllerLiveData.value?.currentBackStackEntry?.destination?.id

        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            if (currentGeneralDestinationId != R.id.mainFragment) {
                navigationManager.generalNavController.popBackStack()
            } else {
                if (currentMainDestinationId == CatalogR.id.catalogFragment
                    || currentMainDestinationId == OrdersR.id.ordersFragment
                    || currentMainDestinationId == FavoritesR.id.favoritesFragment
                    || currentMainDestinationId == BasketR.id.basketFragment
                ) {
                    navigationManager.onBottomNavBarRestore.value = MainR.id.home_graph
                } else {
                    if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                        super.onBackPressed()
                    }
                }
            }
        }
    }
}