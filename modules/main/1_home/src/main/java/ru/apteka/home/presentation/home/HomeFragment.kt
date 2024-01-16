package ru.apteka.home.presentation.home

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.home.R
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import ru.apteka.home.presentation.home.adapters.CategoriesAdapter
import ru.apteka.home.presentation.home.adapters.OrderCardAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewAdapter
import ru.apteka.listing_api.api.LISTING_ARGUMENT
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.listing_api.R as ListingApiR
import ru.apteka.pharmacies_map_api.R as PharmaciesMapApiR
import ru.apteka.product_card_api.R as ProductCardApiR

/**
 * Представляет фрагмент "Главная".
 */
@SuppressLint("ResourceType")
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {

    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.home_fragment

    private val ordersAdapter by lazy {
        CompositeDelegateAdapter(
            OrderCardAdapter(::onOrderCardClick)
        )
    }

    private val advertsAdapter by lazy {
        CompositeDelegateAdapter(
            AdvertCardViewAdapter(::onAdvertCardClick)
        )
    }

    private val promotionsAdapter by lazy {
        CompositeDelegateAdapter(
            PromotionCardViewAdapter(::onPromotionCardClick)
        )
    }

    private val categoriesAdapter by lazy {
        CompositeDelegateAdapter(
            CategoriesAdapter(::onCategoriesClick)
        )
    }

    private val flipEnterAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_enter_anim) as AnimatorSet
    }

    private val flipExitAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_exit_anim) as AnimatorSet
    }

    private val flipPopEnterAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_pop_enter_anim) as AnimatorSet
    }

    private val flipPopExitAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_pop_exit_anim) as AnimatorSet
    }


    override fun onViewBindingInflated(binding: HomeFragmentBinding) {
        if (!viewModel.navigationManager.isHomeFront.value!!) {
            binding.llBonusProgram.bringToFront()
        }
        binding.viewModel = viewModel
        binding.rvOrders.adapter = ordersAdapter
        binding.rvAdvert.adapter = advertsAdapter
        binding.rvPromotion.adapter = promotionsAdapter
        binding.rvCategories.adapter = categoriesAdapter

        binding.homePharmaciesMap.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                PharmaciesMapApiR.id.pharmacies_map_graph, bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }

        binding.svHome.canScrolling = false


        binding.tvHomeProductsDayAll.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ListingApiR.id.listing_graph, bundleOf(
                    LISTING_ARGUMENT to "Товары дня"
                )
            )
        }

        binding.homeProductsDay1.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardApiR.id.product_card_graph, bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.productsDay.value!![0].product
                )
            )
        }

        binding.homeProductsDay2.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardApiR.id.product_card_graph, bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.productsDay.value!![1].product
                )
            )
        }

        binding.tvHomePromotionsAll.setOnClickListener {

        }

        binding.tvHomeProductsDiscountAll.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ListingApiR.id.listing_graph, bundleOf(
                    LISTING_ARGUMENT to "Товары со скидкой"
                )
            )
        }

        binding.homeProductsDiscount1.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardApiR.id.product_card_graph, bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.productsDiscount.value!![0].product
                )
            )
        }

        binding.homeProductsDiscount2.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardApiR.id.product_card_graph, bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.productsDiscount.value!![1].product
                )
            )
        }

        binding.homeMenuBrands.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.onSelectItemMenu(
                ru.apteka.components.R.id.brands_graph,
                bundleOf()
            )
        }


        binding.bonusProgramHistoryAll.btn.setOnClickListener {

        }

        //viewModel.navigationManager.onStartAnimCompleted = {
            viewModel.productsDay.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.homeProductsDay1.model = it[0]
                    binding.homeProductsDay2.model = it[1]
                }
            }

            viewModel.productsDiscount.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.homeProductsDiscount1.model = it[0]
                    binding.homeProductsDiscount2.model = it[1]
                }
            }

            viewModel.ordersCard.observe(viewLifecycleOwner) {
                ordersAdapter.swapData(it)
            }

            viewModel.adverts.observe(viewLifecycleOwner) {
                advertsAdapter.swapData(it)
                if (it.isNotEmpty()) {
                    lifecycleScope.launchIO { recyclerAutoScroll(binding.rvAdvert) }
                }
            }

            viewModel.promotions.observe(viewLifecycleOwner) {
                promotionsAdapter.swapData(it)
            }

            viewModel.categories.observe(viewLifecycleOwner) {
                categoriesAdapter.swapData(it)
            }
        //}

        binding.rlHomeScreen.cameraDistance = 8000.dp.toFloat()
        binding.llBonusProgram.cameraDistance = 8000.dp.toFloat()
        viewModel.navigationManager.onFabClick = {
            if (viewModel.navigationManager.isHomeFront.value!!) {
                flipExitAnim.apply {
                    setTarget(binding.rlHomeScreen)
                    start()
                }
                flipEnterAnim.apply {
                    setTarget(binding.llBonusProgram)
                    start()
                }
                binding.llBonusProgram.bringToFront()
                viewModel.navigationManager.isHomeFront.value = false
            } else {
                flipPopExitAnim.apply {
                    setTarget(binding.llBonusProgram)
                    start()
                }
                flipPopEnterAnim.apply {
                    setTarget(binding.rlHomeScreen)
                    start()
                }
                binding.rlHomeScreen.bringToFront()
                viewModel.navigationManager.isHomeFront.value = true
            }
        }

        binding.mbHomeAuth.setOnClickListener {
            viewModel.navigationManager.goToAuth()
        }
    }

    private fun onOrderCardClick(item: OrderModel) {

    }

    private fun onAdvertCardClick() {

    }

    private fun onPromotionCardClick() {

    }

    private fun onCategoriesClick() {

    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(true)
        binding.homeToolbar.apply {
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.addView(
                DataBindingUtil.inflate<ToolbarMenuBinding>(
                    layoutInflater,
                    ru.apteka.components.R.layout.toolbar_menu,
                    null,
                    false
                ).apply {
                    ivMenuSearch.setOnClickListener {
                        viewModel.navigationManager.showSearchProduct()
                    }
                }.root
            )
        }
        binding.homeToolbar.toolbar.setLogo(R.drawable.logo)

        binding.bonusProgramToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.bonus_program_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.onFabClick()
                //viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}

