package ru.apteka.social

import android.animation.ValueAnimator
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
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
import ru.apteka.components.data.services.message_notice_service.MessageService
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.utils.addOnAnimationEndListener
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchAfter
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.onAnimationEndListener
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.data.utils.setExtraTint
import ru.apteka.components.data.utils.visibleIf
import ru.apteka.components.ui.BottomSheet
import ru.apteka.main.data.CircleEdgeTreatment
import ru.apteka.main.data.setupWithNavController
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.social.databinding.ActivityMainBinding
import ru.apteka.social.databinding.MenuNavigationViewBinding
import ru.apteka.social.presentation.auth.AuthActivity
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
    lateinit var messageService: MessageService

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

    private val viewModel: ActivityViewModel by viewModels()

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
                        vStartAnimRightStartPosition,
                        vStartAnimRightEndPosition
                    ).apply {
                        addUpdateListener { valueAnimator ->
                            binding.vStartAnimRight.x = valueAnimator.animatedValue as Float
                        }
                        duration = 1500
                    }.start()

                    ValueAnimator.ofFloat(
                        vStartAnimLeftStartPosition,
                        vStartAnimLeftEndPosition
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
            0f,
            1f
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
            0f,
            1f
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
        viewModel.navigationManager.onStartAnimCompleted()
        lifecycleScope.launchAfter(1000) {
            mainThread {
                ValueAnimator.ofFloat(
                    1f,
                    0f
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

        // startAnim()

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

        viewModel.navigationManager.apply {
            goToAuth = {
                goToAuthActivity()
            }
            generalNavController = this@MainActivity.generalNavController
            onBottomAppBarShowed = {
                if (it && generalNavController.currentBackStackEntry?.destination?.id == R.id.featureFragment) {
                    binding.bottomAppBar.performShow()
                    binding.bottomAppBarFab.show()
                } else {
                    binding.bottomAppBar.performHide()
                    binding.bottomAppBarFab.hide()
                }
            }
        }

        binding.bottomAppBarModel = viewModel.navigationManager.bottomAppBarModel

        binding.bottomAppBarFab.addOnShowAnimationListener(
            onAnimationEndListener {
                binding.bottomAppBar.visibility = View.VISIBLE
            }
        )

        binding.bottomAppBarFab.addOnHideAnimationListener(
            onAnimationEndListener {
                binding.bottomAppBar.visibility = View.GONE
            }
        )

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
            val controller = viewModel.navigationManager.currentBottomNavControllerLiveData.value!!
            if (controller.graph.id == ru.apteka.components.R.id.main_home_graph) {
                viewModel.navigationManager.onFabClick()
            } else {
                viewModel.navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.main_home_graph)
            }
        }

        binding.bottomNavigationCatalogItem.item.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.apply {
                onItemSelected(item_2.itemId)
            }
        }

        binding.bottomNavigationStocksItem.item.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.apply {
                onItemSelected(item_3.itemId)
            }
        }

        binding.bottomNavigationBasketItem.item.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.apply {
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
                                    ComponentsR.id.profile_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCity.title = viewModel.userPreferences.city?.name
                                ?: getString(ComponentsR.string.city_not_selected)
                            binding.appMenuItemCity.item.setOnClickListener {
                                viewModel.navigationManager.generalNavController.navigateWithAnim(
                                    ru.apteka.choosing_city_api.R.id.choosing_city_graph,
                                )
                                viewModel.bottomSheetService.close()
                            }

                            binding.appMenuItemMyOrders.apply {
                                count = 10
                                item.setOnClickListener {
                                    viewModel.navigationManager.onSelectItemMenu(
                                        ComponentsR.id.orders_graph,
                                        bundleOf()
                                    )
                                    viewModel.bottomSheetService.close()
                                }
                            }

                            binding.appMenuItemNotifications.apply {
                                count = 33
                                item.setOnClickListener {
                                    viewModel.navigationManager.onSelectItemMenu(
                                        ComponentsR.id.notifications_graph,
                                        bundleOf()
                                    )
                                }
                            }

                            binding.appMenuItemLoyaltyProgram.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.loyalty_program_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemReferralProgram.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.referral_program_graph,
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
                                    ComponentsR.id.about_company_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemWorkWithUs.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.work_with_us_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCustomers.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.customers_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemSymptomsAndDiseases.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.symptoms_diseases_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemBrands.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.brands_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemCharity.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.charity_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemContacts.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
                                    ComponentsR.id.contacts_graph,
                                    bundleOf()
                                )
                            }

                            binding.appMenuItemLegalDocuments.item.setOnClickListener {
                                viewModel.navigationManager.onSelectItemMenu(
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
                                                    Uri.parse(
                                                        "market://details?id=${it.applicationContext.packageName}"
                                                    )
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
                                                            Uri.parse(
                                                                "http://play.google.com/store/apps/details?id=${it.applicationContext.packageName}"
                                                            )
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

        viewModel.navigationManager.onSelectItemMenu = { graphId, bundle ->
            if (viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.currentBackStackEntry!!.destination.parent!!.id != graphId) {
                viewModel.navigationManager.bottomAppBarModel.onItemSelected(viewModel.navigationManager.bottomAppBarModel.item_5.itemId)
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

        viewModel.navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.observe(
            this
        ) {
            setupBottomNavigationBar()
        }

        viewModel.navigationManager.isHomeFront.observe(this) {
            setBottomAppBarFabState(it)
        }

        viewModel.basketService.totalCount.observe(this) { count ->
            binding.nbTab4.setNumber(
                if (count > 0) {
                    count
                } else {
                    null
                }
            )
        }

        lifecycleScope.launchIO {
            messageService.commonDialog.collect { dialogModel ->
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
            messageService.commonToast.collect { message ->
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
            viewModel.bottomSheetService.bottomSheet.collect { bottomSheet ->
                mainThread {
                    viewModel.bottomSheetService.bottomSheetDialog = BottomSheet.newInstance(
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
            viewModel.navigationManager.generalNavController.currentBackStackEntry?.destination?.id
        val currentMainDestinationId =
            viewModel.navigationManager.currentBottomNavControllerLiveData.value?.currentBackStackEntry?.destination?.id
        val previousMainDestinationId =
            viewModel.navigationManager.currentBottomNavControllerLiveData.value?.previousBackStackEntry?.destination?.id

        if (currentGeneralDestinationId != R.id.featureFragment) {
            viewModel.navigationManager.generalNavController.popBackStack()
        } else {
            if (currentMainDestinationId == CatalogR.id.catalogFragment ||
                currentMainDestinationId == StocksR.id.stocksFragment ||
                currentMainDestinationId == BasketR.id.basketFragment ||
                previousMainDestinationId == MenuR.id.menuFragment
            ) {
                if (previousMainDestinationId == MenuR.id.menuFragment) {
                    viewModel.navigationManager.currentBottomNavControllerLiveData.value?.popBackStack()
                }
                viewModel.navigationManager.bottomAppBarModel.onItemSelected(ComponentsR.id.main_home_graph)
            } else {
                if (!viewModel.navigationManager.isHomeFront.value!!) {
                    viewModel.navigationManager.onFabClick()
                } else {
                    if (viewModel.navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
                        super.onBackPressed()
                    }
                }
            }
        }
    }

    private fun setBottomAppBarFabState(isHomeFront: Boolean) {
        if (isHomeFront) {
            binding.bottomAppBarFab.apply {
                setImageResource(ComponentsR.drawable.ic_card)
                setExtraTint(
                    ContextCompat.getColor(
                        this@MainActivity,
                        ComponentsR.color.color_primary
                    )
                )
            }
            binding.bottomNavigationHomeItem.tvItem.text = getString(ComponentsR.string.menu_label_1_2)
        } else {
            binding.bottomAppBarFab.apply {
                setImageResource(ComponentsR.drawable.ic_home)
                setExtraTint(
                    ContextCompat.getColor(
                        this@MainActivity,
                        ComponentsR.color.color_primary
                    )
                )
            }
            binding.bottomNavigationHomeItem.tvItem.text = getString(ComponentsR.string.menu_label_1)
        }
    }

    private fun setupBottomNavigationBar() {
        viewModel.navigationManager.currentBottomNavControllerLiveData =
            binding.bottomAppBar.setupWithNavController(
                bottomAppBarModel = viewModel.navigationManager.bottomAppBarModel,
                navGraphIds = listOf(
                    HomeR.navigation.main_home_graph,
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
