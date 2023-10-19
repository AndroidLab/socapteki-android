package ru.apteka.home.presentation.home

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.composite_delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.choosing_city_api.R as ChoosingCityApiR
import ru.apteka.pharmacies_map_api.R as PharmaciesMapApiR
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.home.presentation.home.adapters.AdvertCardViewAdapter
import ru.apteka.home.presentation.home.adapters.AdvertCardViewSkeletonAdapter
import ru.apteka.home.presentation.home.adapters.OtherCardViewAdapter
import ru.apteka.home.presentation.home.adapters.OtherCardViewSkeletonAdapter
import ru.apteka.home.presentation.home.adapters.ProductCardViewAdapter
import ru.apteka.home.presentation.home.adapters.ProductCardViewSkeletonAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewAdapter
import ru.apteka.home.presentation.home.adapters.PromotionCardViewSkeletonAdapter


/**
 * Представляет фрагмент "Главная".
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.home_fragment

    private val skeletons = listOf(Skeleton(), Skeleton())

    private val advertsAdapter by lazy {
        CompositeDelegateAdapter(
            AdvertCardViewAdapter(::onAdvertCardClick),
            AdvertCardViewSkeletonAdapter()
        )
    }

    private val promotionsAdapter by lazy {
        CompositeDelegateAdapter(
            PromotionCardViewAdapter(::onPromotionCardClick),
            PromotionCardViewSkeletonAdapter()
        )
    }

    private val productsDayAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(::onProductsCardClick),
            ProductCardViewSkeletonAdapter()
        )
    }

    private val productsDiscountAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(::onProductsCardClick),
            ProductCardViewSkeletonAdapter()
        )
    }

    private val othersAdapter by lazy {
        CompositeDelegateAdapter(
            OtherCardViewAdapter(::onOtherCardClick),
            OtherCardViewSkeletonAdapter()
        )
    }


    override fun onViewBindingInflated(binding: HomeFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvAdvert.adapter = advertsAdapter
        binding.rvPromotions.adapter = promotionsAdapter
        binding.rvProductsDay.adapter = productsDayAdapter
        binding.rvProductsDiscount.adapter = productsDiscountAdapter
        binding.rvOther.adapter = othersAdapter


        binding.tvHomeLocationChange.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigate(ChoosingCityApiR.id.choosing_city_graph)
        }

        binding.tvHomeChangePromotionsAll.setOnClickListener {

        }

        binding.tvHomeChangeProductsDayAll.setOnClickListener {

        }

        binding.tvHomeChangeProductsDiscountAll.setOnClickListener {

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
        mActivity.supportActionBar!!.apply {
            title = ""
            setLogo(ComponentsR.drawable.logo)
        }
    }

    override fun onStop() {
        super.onStop()
        mActivity.supportActionBar!!.setLogo(null)
    }
}