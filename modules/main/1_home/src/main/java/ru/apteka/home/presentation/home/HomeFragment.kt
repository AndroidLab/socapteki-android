package ru.apteka.home.presentation.home

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.ui.boundcy.BouncyNestedScrollView
import ru.apteka.components.ui.boundcy.util.OnOverPullListener
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.home.R
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import ru.apteka.home.presentation.home.adapters.OtherCardViewAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.components.R as ComponentsR
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

        binding.homePromotions.header.btn.setOnClickListener {

        }

        binding.homeProductsDay.header.btn.setOnClickListener {

        }

        binding.homeProductsDiscount.header.btn.setOnClickListener {

        }

        binding.homeMenuBrands.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(ComponentsR.id.brands_graph)
        }

        binding.homeMenuPartners.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(ComponentsR.id.partners_graph)
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