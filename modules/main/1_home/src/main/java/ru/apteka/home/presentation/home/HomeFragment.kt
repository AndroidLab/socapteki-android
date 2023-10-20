package ru.apteka.home.presentation.home

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.delegate_adapter.SkeletonAdapter
import ru.apteka.home.R
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import ru.apteka.home.presentation.home.adapters.OtherCardViewAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewAdapter
import ru.apteka.choosing_city_api.R as ChoosingCityApiR
import ru.apteka.components.R as ComponentsR
import ru.apteka.pharmacies_map_api.R as PharmaciesMapApiR


/**
 * Представляет фрагмент "Главная".
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.home_fragment

    private val skeletons = listOf(Skeleton(), Skeleton(), Skeleton())

    private val advertsAdapter by lazy {
        CompositeDelegateAdapter(
            AdvertCardViewAdapter(::onAdvertCardClick),
            SkeletonAdapter(284.dp, 136.dp)
        )
    }

    private val promotionsAdapter by lazy {
        CompositeDelegateAdapter(
            PromotionCardViewAdapter(::onPromotionCardClick),
            SkeletonAdapter(180.dp, 140.dp)
        )
    }

    private val productsDayAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(::onProductsCardClick),
            SkeletonAdapter(166.dp, 340.dp)
        )
    }

    private val productsDiscountAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(::onProductsCardClick),
            SkeletonAdapter(166.dp, 340.dp)
        )
    }

    private val othersAdapter by lazy {
        CompositeDelegateAdapter(
            OtherCardViewAdapter(::onOtherCardClick),
            SkeletonAdapter(250.dp, 230.dp)
        )
    }


    override fun onViewBindingInflated(binding: HomeFragmentBinding) {
        binding.viewModel = viewModel
        binding.homeAdvert.rv.adapter = advertsAdapter
        binding.homePromotions.rv.adapter = promotionsAdapter
        binding.homeProductsDay.rv.adapter = productsDayAdapter
        binding.homeProductsDiscount.rv.adapter = productsDiscountAdapter
        binding.homeOther.rv.adapter = othersAdapter

        binding.tvHomeLocationChange.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigate(ChoosingCityApiR.id.choosing_city_graph)
        }

        binding.homePromotions.header.btn.setOnClickListener {

        }

        binding.homeProductsDay.header.btn.setOnClickListener {

        }

        binding.homeProductsDiscount.header.btn.setOnClickListener {

        }

        binding.homeMenuBrands.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigate(ComponentsR.id.brands_graph)
        }

        binding.homeMenuPharmacies.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigate(PharmaciesMapApiR.id.pharmacies_map_graph)
        }

        binding.homeMenuAdvantages.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigate(ComponentsR.id.advantages_graph)
        }

        binding.homeMenuPartners.homeMenuItem.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigate(ComponentsR.id.partners_graph)
        }


        viewModel.adverts.observe(viewLifecycleOwner) {
            advertsAdapter.swapData(
                it.ifEmpty { skeletons }
            )
            if (it.isNotEmpty()) {
                lifecycleScope.launchIO { recyclerAutoScroll(binding.homeAdvert.rv) }
            }
        }

        viewModel.promotions.observe(viewLifecycleOwner) {
            promotionsAdapter.swapData(
                it.ifEmpty { skeletons }
            )
        }

        viewModel.productsDay.observe(viewLifecycleOwner) {
            productsDayAdapter.swapData(
                it.ifEmpty { skeletons }
            )
        }

        viewModel.productsDiscount.observe(viewLifecycleOwner) {
            productsDiscountAdapter.swapData(
                it.ifEmpty { skeletons }
            )
        }

        viewModel.others.observe(viewLifecycleOwner) {
            othersAdapter.swapData(
                it.ifEmpty { skeletons }
            )
        }


    }

    private fun onAdvertCardClick() {

    }

    private fun onPromotionCardClick() {

    }

    private fun onProductsCardClick() {

    }

    private fun onOtherCardClick() {

    }

    override fun onResume() {
        super.onResume()
        binding.homeToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_menu)
            toolbar.setLogo(ComponentsR.drawable.logo)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.drawerLayout.open()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.addView(
                DataBindingUtil.inflate<ToolbarMenuBinding>(
                    layoutInflater,
                    ComponentsR.layout.toolbar_menu,
                    null,
                    false
                ).apply {
                    ivMenuSearch.setOnClickListener {

                    }
                    ivMenuDoctor.setOnClickListener {

                    }
                    ivMenuAuth.setOnClickListener {
                        viewModel.navigationManager.navigateToAuthActivity()
                    }
                }.root
            )
        }

    }

}