package ru.apteka.social

import android.animation.Animator
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
import androidx.navigation.NavOptions
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
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.setImageTint
import ru.apteka.components.ui.BottomSheet
import ru.apteka.main.data.CircleEdgeTreatment
import ru.apteka.main.data.setupWithNavController
import ru.apteka.main.databinding.MenuNavigationViewBinding
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.presentation.auth.AuthActivity
import javax.inject.Inject
import ru.apteka.basket.R as BasketR
import ru.apteka.catalog.R as CatalogR
import ru.apteka.stocks.R as StocksR
import ru.apteka.main.R as MainR
import ru.apteka.menu.R as MenuR
import ru.apteka.components.R as ComponentsR


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
        binding.lifecycleOwner = this

        lifecycleScope.launchIO {
            //delay(6000)
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
        }

        navigationManager.apply {
            generalNavController = this@MainActivity.generalNavController
        }
        binding.bottomAppBarModel = navigationManager.bottomAppBarModel

        navigationManager.onBottomAppBarShowed = {
            if (it) {
                binding.bottomAppBar.performShow()
                binding.bottomAppBarFab.show()
            } else {
                binding.bottomAppBar.performHide()
                binding.bottomAppBarFab.hide()
            }
        }

        binding.bottomAppBarFab.addOnShowAnimationListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                binding.bottomAppBar.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        binding.bottomAppBarFab.addOnHideAnimationListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                binding.bottomAppBar.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
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
                if (controller.currentDestination!!.id == ru.apteka.home.R.id.bonusProgramFragment) {
                    controller.popBackStack()
                    binding.bottomAppBarFab.apply {
                        setImageResource(ru.apteka.components.R.drawable.ic_home)
                        setImageTint(
                            ContextCompat.getColor(
                                this@MainActivity,
                                ru.apteka.components.R.color.color_primary
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
                        ru.apteka.home.R.id.bonusProgramFragment,
                        bundleOf(),
                        navOptions
                    )
                    binding.bottomAppBarFab.apply {
                        setImageResource(ru.apteka.main.R.drawable.ic_card)
                        setImageTint(
                            ContextCompat.getColor(
                                this@MainActivity,
                                ru.apteka.components.R.color.color_primary
                            )
                        )
                    }
                }
            } else {
                navigationManager.bottomAppBarModel.onItemSelected(ru.apteka.components.R.id.home_graph)
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

                            binding.appMenuItemProfile.item.setOnClickListener {
                                navigationManager.onSelectItemMenu(
                                    ru.apteka.components.R.id.profile_graph,
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
                                    navigationManager.generalNavController.navigateWithAnim(
                                        ru.apteka.orders_api.R.id.orders_graph,
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
                                                layoutId = ru.apteka.main.R.layout.rate_dialog
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
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        AuthActivity::class.java
                                    )
                                )
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

        basketService.totalCount.observe(this) { count ->
            binding.nbTab4.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
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
                navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.home_graph)
            } else {
                if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                    super.onBackPressed()
                }
            }
        }

    }

    private fun setupBottomNavigationBar() {
        navigationManager.currentBottomNavControllerLiveData =
            binding.bottomAppBar.setupWithNavController(
                bottomAppBarModel = navigationManager.bottomAppBarModel,
                navGraphIds = listOf(
                    ru.apteka.home.R.navigation.home_graph,
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