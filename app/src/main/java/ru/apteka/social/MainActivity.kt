package ru.apteka.social

import android.animation.ValueAnimator
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.basket_service.BasketService
import ru.apteka.components.data.services.message_notice_service.BottomSheetService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.addOnAnimationEndListener
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchAfter
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.onAnimationEndListener
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.data.utils.setImageTint
import ru.apteka.components.data.utils.subStringByRegex
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BottomSheet
import ru.apteka.main.data.CircleEdgeTreatment
import ru.apteka.main.data.setupWithNavController
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.social.data.services.AppUpdateService
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.databinding.DialogNewVersionFileBinding
import ru.apteka.social.databinding.MenuNavigationViewBinding
import ru.apteka.social.presentation.auth.AuthActivity
import java.util.Locale
import javax.inject.Inject
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.components.R as ComponentsR
import ru.apteka.home.R as HomeR
import ru.apteka.main.R as MainR
import ru.apteka.menu.R as MenuR
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

    @Inject
    lateinit var basketService: BasketService

    @Inject
    lateinit var appUpdateService: AppUpdateService

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


    private val vStartAnimLeftStartPosition = (-150).dp.toFloat()
    private val vStartAnimLeftEndPosition = (screenWidth / 2 - 150.dp).toFloat()
    private val vStartAnimRightStartPosition = screenWidth.toFloat()
    private val vStartAnimRightEndPosition = (screenWidth / 2).toFloat()

    private fun startAnim() {
        binding.flStartAnim.visibility = View.VISIBLE
        lifecycleScope.launchIO {
            launchAfter(750) {
                mainThread {
                    binding.vStartAnimLeft.visibility = View.VISIBLE
                    binding.vStartAnimRight.visibility = View.VISIBLE
                    ValueAnimator.ofFloat(
                        vStartAnimRightStartPosition, vStartAnimRightEndPosition
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            binding.vStartAnimRight.x = valueAnimator.animatedValue as Float
                        }
                        duration = 1500
                    }.start()

                    ValueAnimator.ofFloat(
                        vStartAnimLeftStartPosition, vStartAnimLeftEndPosition
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            binding.vStartAnimLeft.x = valueAnimator.animatedValue as Float
                        }
                        addOnAnimationEndListener {
                            startLogoAnim()
                        }
                        duration = 1500
                    }.start()
                }
            }
        }
    }

    private fun startLogoAnim() {
        ValueAnimator.ofFloat(
            0f, 1f
        ).apply {
            addUpdateListener { valueAnimator ->
                binding.ivStartAnimLogo.alpha = valueAnimator.animatedValue as Float
            }
            addOnAnimationEndListener {
                startDescAnim()
            }
            duration = 750
        }.start()
    }

    private fun startDescAnim() {
        ValueAnimator.ofFloat(
            0f, 1f
        ).apply {
            addUpdateListener { valueAnimator ->
                binding.tvStartAnimLogo.alpha = valueAnimator.animatedValue as Float
                binding.tvStartAnimDesc.alpha = valueAnimator.animatedValue as Float
            }
            addOnAnimationEndListener {
                startFadeOutAnim()
            }
            duration = 750
        }.start()
    }

    private fun startFadeOutAnim() {
        navigationManager.onStartAnimCompleted()
        lifecycleScope.launchAfter(1000) {
            mainThread {
                ValueAnimator.ofFloat(
                    1f, 0f
                ).apply {
                    addUpdateListener { valueAnimator ->
                        val value = valueAnimator.animatedValue as Float
                        binding.flStartAnim.alpha = value
                        binding.flStartAnim.visibleIf(value > 0)
                    }
                    duration = 400
                }.start()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        binding.lifecycleOwner = this

        lifecycleScope.launchIO {
            appUpdateService.checkUpdate()
        }

        //startAnim()

        /*lifecycleScope.launchIO {
            delay(6000)
            mainThread {
                ValueAnimator.ofFloat(
                    1f, 0f
                ).apply {
                    addUpdateListener { valueAnimator ->
                        binding.ivStartAnim.alpha = valueAnimator.animatedValue as Float
                    }
                    duration = 400
                }.start()
            }
        }*/

        navigationManager.apply {
            goToAuth = {
                goToAuthActivity()
            }
            generalNavController = this@MainActivity.generalNavController
            onBottomAppBarShowed = {
                if (it) {
                    binding.bottomAppBar.performShow()
                    binding.bottomAppBarFab.show()
                } else {
                    binding.bottomAppBar.performHide()
                    binding.bottomAppBarFab.hide()
                }
            }
        }

        binding.bottomAppBarModel = navigationManager.bottomAppBarModel

        binding.bottomAppBarFab.addOnShowAnimationListener(onAnimationEndListener {
            binding.bottomAppBar.visibility = View.VISIBLE
        })

        binding.bottomAppBarFab.addOnHideAnimationListener(onAnimationEndListener {
            binding.bottomAppBar.visibility = View.GONE
        })

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
                        this@MainActivity,
                        ComponentsR.color.white
                    )
                )
                paintStyle = Paint.Style.FILL
            }
        }

        binding.bottomAppBarFab.setOnClickListener {
            val controller = navigationManager.currentBottomNavControllerLiveData.value!!
            if (controller.graph.id == ru.apteka.components.R.id.home_graph) {
                navigationManager.onFabClick()
            } else {
                navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.home_graph)
            }
        }

        binding.bottomNavigationCatalogItem.item.setOnClickListener {
            navigationManager.bottomAppBarModel.apply {
                onItemSelected(item_2.itemId)
            }
        }

        binding.bottomNavigationStocksItem.item.setOnClickListener {
            navigationManager.bottomAppBarModel.apply {
                onItemSelected(item_3.itemId)
            }
        }

        binding.bottomNavigationBasketItem.item.setOnClickListener {
            navigationManager.bottomAppBarModel.apply {
                onItemSelected(item_4.itemId)
            }
        }

        binding.bottomNavigationMenuItem.item.setOnClickListener {
            bottomSheetService.show(
                BottomSheetModel(
                    binding = MenuNavigationViewBinding.inflate(layoutInflater, null, false)
                        .also { binding ->
                            binding.account = accountsPreferences.account
                            binding.appUpdateService = appUpdateService

                            binding.appMenuItemProfile.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.profile_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCity.title = userPreferences.city?.name
                                ?: getString(ru.apteka.main.R.string.menu_city_not_selected)
                            binding.appMenuItemCity.item.setOnClickListener {
                                navigationManager.generalNavController.navigateWithAnim(
                                    ru.apteka.choosing_city_api.R.id.choosing_city_graph,
                                )
                                bottomSheetService.close()
                            }

                            binding.appMenuItemMyOrders.apply {
                                count = 10
                                item.setOnClickListener {
                                    navigationManager.onSelectItemMenu(
                                        ComponentsR.id.orders_graph,
                                        bundleOf()
                                    )
                                    bottomSheetService.close()
                                }
                            }

                            binding.appMenuItemNotifications.apply {
                                count = 33
                                item.setOnClickListener {
                                    navigationManager.onSelectItemMenu(
                                        ComponentsR.id.notifications_graph,
                                        bundleOf()
                                    )
                                }
                            }

                            binding.appMenuItemLoyaltyProgram.item.setOnClickListener {
                                //navigate(ru.apteka.about_company_api.R.id.about_company_graph)
                            }

                            binding.appMenuItemReferralProgram.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.referral_program_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemPharmacies.item.setOnClickListener {
                                navigationManager.generalNavController.navigateWithAnim(
                                    ru.apteka.pharmacies_map_api.R.id.pharmacies_map_graph,
                                    bundleOf(
                                        PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                                    )
                                )
                                bottomSheetService.close()
                            }

                            binding.appMenuItemAboutCompany.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.about_company_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemWorkWithUs.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.work_with_us_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCustomers.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.customers_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemSymptomsAndDiseases.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.symptoms_diseases_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemBrands.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.brands_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCharity.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.charity_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemContacts.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.contacts_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemLegalDocuments.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ComponentsR.id.legal_documents_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemRateApp.item.setOnClickListener {
                                showCommonDialog(
                                    CommonDialogModel(
                                        fragmentManager = supportFragmentManager,
                                        dialogModel = DialogModel(
                                            bodyContent = BodyContentModel(
                                                layoutId = R.layout.rate_dialog
                                            ) { dialog, binding -> },
                                            buttonCancel = DialogButtonModel(
                                                text = ru.apteka.main.R.string.rate_app_cancel
                                            ),
                                            buttonConfirm = DialogButtonModel(
                                                text = ru.apteka.main.R.string.rate_app_confirm
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

                            binding.mbMenuAuth.setOnClickListener {
                                goToAuthActivity()
                            }
                        },
                    useScrollableContainer = false
                )
            )
        }

        navigationManager.onSelectItemMenu = { graphId, bundle ->
            if (navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.parent!!.id != graphId) {
                navigationManager.bottomAppBarModel.onItemSelected(navigationManager.bottomAppBarModel.item_5.itemId)
                lifecycleScope.launchMain {
                    delay(100)
                    if (navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.id != MenuR.id.menuFragment) {
                        navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(
                            MenuR.id.menuFragment,
                            false
                        )
                    }
                    navigationManager.currentBottomNavControllerLiveData.value!!.navigate(
                        graphId, bundle
                    )
                }
            }
            bottomSheetService.close()
        }

        navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            this
        ) {
            setupBottomNavigationBar()
        }

        navigationManager.isHomeFront.observe(this) {
            setBottomAppBarFabState(it)
        }

        basketService.totalCount.observe(this) { count ->
            binding.nbTab4.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
            )
        }

        appUpdateService.newVersionFile.observe(this) { newVersionFile ->
            if (newVersionFile != null) {
                val lastVersionChecked = userPreferences.lastVersionChecked
                if (lastVersionChecked == -1f || newVersionFile.version > lastVersionChecked) {
                    showCommonDialog(
                        CommonDialogModel(
                            fragmentManager = supportFragmentManager,
                            DialogModel(
                                title = R.string.new_version_file_title,
                                bodyContent = BodyContentModel(
                                    layoutId = R.layout.dialog_new_version_file,
                                ) { dialog, binding ->
                                    binding as DialogNewVersionFileBinding
                                    binding.text = String.format(
                                        getString(
                                            R.string.new_version_file_message
                                        ),
                                        newVersionFile.version,
                                        String.format(
                                            Locale.US,
                                            "%.2f",
                                            newVersionFile.doc.size / 1024.0 / 1024.0
                                        )
                                    )
                                    binding.isLoading = appUpdateService.isLoading
                                    binding.newVersionFileUpdateBtn.setOnClickListener {
                                        appUpdateService.updateApp()
                                        dialog.dismiss()
                                    }
                                },
                                onDismiss = {
                                    userPreferences.lastVersionChecked =
                                        newVersionFile.version
                                }
                            )
                        )
                    )
                }
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

    private fun goToAuthActivity() {
        startActivity(
            Intent(
                this@MainActivity,
                AuthActivity::class.java
            )
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
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

        if (currentGeneralDestinationId != R.id.featureFragment) {
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
                navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.home_graph)
            } else {
                if (!navigationManager.isHomeFront.value!!) {
                    navigationManager.onFabClick()
                } else {
                    if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                        super.onBackPressed()
                    }
                }
            }
        }
    }

    private fun setBottomAppBarFabState(isHomeFront: Boolean) {
        if (isHomeFront) {
            binding.bottomAppBarFab.apply {
                setImageResource(ComponentsR.drawable.ic_home)
                setImageTint(
                    ContextCompat.getColor(
                        this@MainActivity,
                        ComponentsR.color.color_primary
                    )
                )
            }
        } else {
            binding.bottomAppBarFab.apply {
                setImageResource(MainR.drawable.ic_card)
                setImageTint(
                    ContextCompat.getColor(
                        this@MainActivity,
                        ComponentsR.color.color_primary
                    )
                )
            }
        }
    }

    private fun setupBottomNavigationBar() {
        navigationManager.currentBottomNavControllerLiveData =
            binding.bottomAppBar.setupWithNavController(
                bottomAppBarModel = navigationManager.bottomAppBarModel,
                navGraphIds = listOf(
                    HomeR.navigation.home_graph,
                    CatalogR.navigation.catalog_graph,
                    StocksR.navigation.stocks_graph,
                    BasketR.navigation.basket_graph,
                    MenuR.navigation.menu_graph,
                ),
                fragmentManager = supportFragmentManager,
                containerId = MainR.id.nav_host_container,
                intent = intent,
            )
    }

}