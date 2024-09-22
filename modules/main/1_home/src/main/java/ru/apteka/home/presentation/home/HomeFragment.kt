package ru.apteka.home.presentation.home

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.home.R
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import ru.apteka.home.presentation.home.adapters.CategoriesAdapter
import ru.apteka.home.presentation.home.adapters.OrderCardAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewAdapter
import ru.apteka.listing.LISTING_ARGUMENT
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.components.R as ComponentsR
import ru.apteka.listing.R as ListingR
import ru.apteka.order_details_api.R as OrderDetailsApiR
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

    private val productsDayAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsDayCardClick,
                true
            )
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

    private val homeNavController by lazy {
        requireActivity().findNavController(R.id.home_nav_host)
    }

    override fun onViewBindingInflated(binding: HomeFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvOrders.adapter = ordersAdapter
        binding.rvAdvert.adapter = advertsAdapter
        binding.rvProductsDay.adapter = productsDayAdapter
        binding.rvPromotion.adapter = promotionsAdapter
        binding.rvCategories.adapter = categoriesAdapter

        binding.homePharmaciesMap.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                PharmaciesMapApiR.id.pharmacies_map_graph,
                bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }

        binding.tvHomeProductsDayAll.setOnClickListener {
            homeNavController.navigateWithAnim(
                ListingR.id.listing_graph,
                bundleOf(
                    LISTING_ARGUMENT to "Товары дня"
                )
            )
        }

        viewModel.productsDay.observe(viewLifecycleOwner) {
            productsDayAdapter.swapData(it)
        }

        binding.tvHomePromotionsAll.setOnClickListener {
            homeNavController.navigateWithAnim(
                ListingR.id.listing_graph,
                bundleOf(
                    LISTING_ARGUMENT to "Акции"
                )
            )
        }

        binding.tvHomeProductsDiscountAll.setOnClickListener {
            homeNavController.navigateWithAnim(
                ListingR.id.listing_graph,
                bundleOf(
                    LISTING_ARGUMENT to "Товары со скидкой"
                )
            )
        }

        binding.homeProductsDiscount1.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardApiR.id.product_card_graph,
                bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.productsDiscount.value!![0].product
                )
            )
        }

        binding.homeProductsDiscount2.productCardItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ProductCardApiR.id.product_card_graph,
                bundleOf(
                    PRODUCT_CARD_ARGUMENT_PRODUCT to viewModel.productsDiscount.value!![1].product
                )
            )
        }

        binding.homeMenuBrands.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.onSelectItemMenu(
                ComponentsR.id.brands_graph,
                bundleOf()
            )
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

        binding.mbHomeAuth.setOnClickListener {
            viewModel.navigationManager.goToAuth()
        }
    }

    private fun onOrderCardClick(item: OrderModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            OrderDetailsApiR.id.order_details_graph,
            bundleOf(
                ORDER_DETAILS_ARGUMENT to item
            )
        )
    }

    private fun onAdvertCardClick() {
    }

    private fun onProductsDayCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ProductCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
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
                    ComponentsR.layout.toolbar_menu,
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
    }
}
