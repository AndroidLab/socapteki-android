package ru.apteka.home.presentation.home

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.data.utils.setPullForward
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.home.R
import ru.apteka.home.data.models.OrderCardModel
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import ru.apteka.home.presentation.home.adapters.OrderCardAdapter
import ru.apteka.home.presentation.home.adapters.OtherCardViewAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.main_common.R as MainCommonR
import ru.apteka.pharmacies_map_api.R as PharmaciesMapApiR
import ru.apteka.product_card_api.R as productCardApiR


/**
 * Представляет фрагмент "Главная".
 */
@AndroidEntryPoint
class HomeFragment : MainScreenBaseFragment<HomeViewModel, HomeFragmentBinding>() {

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

    private val productsDayAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
        )
    }

    private val productsDiscountAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
        )
    }

    private val othersAdapter by lazy {
        CompositeDelegateAdapter(
            OtherCardViewAdapter(::onOtherCardClick)
        )
    }


    override fun onViewBindingInflated(binding: HomeFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvOrders.adapter = ordersAdapter
        binding.homeAdvert.rv.adapter = advertsAdapter
        binding.homePromotions.rv.adapter = promotionsAdapter
        binding.homeProductsDay.rv.adapter = productsDayAdapter
        binding.homeProductsDiscount.rv.adapter = productsDiscountAdapter
        binding.homeOther.rv.adapter = othersAdapter

        binding.homePharmaciesMap.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                PharmaciesMapApiR.id.pharmacies_map_graph, bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }

        binding.homeProductsDay.rv.setPullForward(binding.homeProductsDay.vForvward) {
            binding.homePromotions.header.btn.performClick()
        }

        binding.homePromotions.header.btn.setOnClickListener {

        }

        binding.homeProductsDay.header.btn.setOnClickListener {

        }

        binding.homeProductsDiscount.header.btn.setOnClickListener {

        }

        binding.homeMenuBrands.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.onSelectItemMenu(MainCommonR.id.brands_graph, bundleOf())
        }


        viewModel.ordersCard.observe(viewLifecycleOwner) {
            ordersAdapter.swapData(it)
        }

        viewModel.adverts.observe(viewLifecycleOwner) {
            advertsAdapter.swapData(it)
            if (it.isNotEmpty()) {
                lifecycleScope.launchIO { recyclerAutoScroll(binding.homeAdvert.rv) }
            }
        }

        viewModel.promotions.observe(viewLifecycleOwner) {
            promotionsAdapter.swapData(it)
        }

        viewModel.productsDay.observe(viewLifecycleOwner) {
            productsDayAdapter.swapData(it)
        }

        viewModel.productsDiscount.observe(viewLifecycleOwner) {
            productsDiscountAdapter.swapData(it)
        }

        viewModel.others.observe(viewLifecycleOwner) {
            othersAdapter.swapData(it)
        }
    }

    private fun onOrderCardClick(item: OrderCardModel) {

    }

    private fun onAdvertCardClick() {

    }

    private fun onPromotionCardClick() {

    }

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            productCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    private fun onOtherCardClick() {

    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(
            binding.homeToolbar,
            onSearchClick = viewModel.navigationManager.showSearchProduct
        )
        binding.homeToolbar.toolbar.setLogo(MainCommonR.drawable.logo)
    }

}