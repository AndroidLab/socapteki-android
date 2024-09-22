package ru.apteka.listing.presentation

import android.animation.ValueAnimator
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.BR
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.addOnAnimationEndListener
import ru.apteka.components.data.utils.bottomSystemNavigationBarHeight
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.screenHeight
import ru.apteka.components.databinding.ProductsSortBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.listing.LISTING_ARGUMENT
import ru.apteka.listing.R
import ru.apteka.listing.data.models.FilterChipModel
import ru.apteka.listing.data.models.FilterType
import ru.apteka.listing.data.models.IFilter
import ru.apteka.listing.databinding.ListingFragmentBinding
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.components.R as ComponentsR
import ru.apteka.product_card_api.R as ProductCardApiR


/**
 * Представляет фрагмент "Листинг".
 */
@AndroidEntryPoint
class ListingFragment : BaseFragment<ListingViewModel, ListingFragmentBinding>() {

    override val viewModel: ListingViewModel by viewModels()
    override val layoutId: Int = R.layout.listing_fragment

    private val withProductsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsDayCardClick,
                true
            )
        )
    }

    private val watchedRecentlyAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsDayCardClick,
                true
            )
        )
    }

    private val args: ListingFragmentArgs by navArgs()

    private val listingProductsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                false
            )
        )
    }

    private var headerAnimationShow: ValueAnimator? = null
    private var headerAnimationHide: ValueAnimator? = null
    private var oldScrollPosition = 0

    private fun showHeaderAnimation(
        onAnimComplete: () -> Unit
    ) {
        resetHeaderAnimation()
        headerAnimationHide = null
        headerAnimationShow = ValueAnimator.ofFloat(
            binding.catalogProductsHeader.translationY,
            0f
        ).apply {
            addUpdateListener { valueAnimator ->
                binding.catalogProductsHeader.translationY = valueAnimator.animatedValue as Float
            }
            addOnAnimationEndListener {
                onAnimComplete()
            }
            duration = 450
            start()
        }
    }

    private fun hideHeaderAnimation(
        onAnimComplete: () -> Unit
    ) {
        resetHeaderAnimation()
        headerAnimationHide = ValueAnimator.ofFloat(
            binding.catalogProductsHeader.translationY,
            -resources.getDimension(R.dimen.catalog_products_header).dp.toFloat(),
        ).apply {
            addUpdateListener { valueAnimator ->
                binding.catalogProductsHeader.translationY = valueAnimator.animatedValue as Float
            }
            addOnAnimationEndListener {
                onAnimComplete()
            }
            duration = 450
            start()
        }
    }

    private fun resetHeaderAnimation() {
        headerAnimationShow?.cancel()
        headerAnimationHide?.cancel()
        headerAnimationShow = null
        headerAnimationHide = null
    }

    override fun onViewBindingInflated(binding: ListingFragmentBinding) {
        Log.d("myL", "ListingFragment")
        binding.viewModel = viewModel
        binding.lifecycle = this
        val navigationBarHeight = requireContext().bottomSystemNavigationBarHeight

        binding.listingProducts.setOnScrollChangeListener { view, i, scrollTopY, i3, i4 ->
            val scrollBottomY = (scrollTopY + screenHeight - navigationBarHeight).toFloat()

            if (scrollTopY > (oldScrollPosition + 200.dp) && (headerAnimationHide == null || headerAnimationHide?.isRunning == false)) {
                hideHeaderAnimation {
                    oldScrollPosition = binding.listingProducts.scrollY
                }
            }
            if ((scrollTopY < 200.dp || scrollTopY < (oldScrollPosition -50.dp)) && (headerAnimationShow == null || headerAnimationShow?.isRunning == false)) {
                showHeaderAnimation {
                    oldScrollPosition = binding.listingProducts.scrollY
                }
            }

            if (scrollTopY > (oldScrollPosition + 200.dp)) {
                viewModel.navigationManager.onBottomAppBarShowed(false)
            }
            if ((scrollTopY < 200.dp || scrollTopY < (oldScrollPosition - 50.dp))) {
                viewModel.navigationManager.onBottomAppBarShowed(true)
            }
        }


        binding.rvListingProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //state
            //0 - Список остановился
            //1 - Начали вращать
            //2 - вращается по инерции
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 0) {
                    oldScrollPosition = binding.listingProducts.scrollY
                }
            }
        })

        binding.catalogProductsSort.setOnClickListener {
            Log.d("myL", "catalogProductsSort")
            viewModel.bottomSheetService.show(
                BottomSheetModel(
                    binding = ProductsSortBinding.inflate(layoutInflater, null, false)
                        .apply {
                            lifecycleOwner = viewLifecycleOwner
                            listingProductFilterModel = viewModel.sortModel
                        }
                )
            )
        }

        binding.catalogProductAllFilters.setOnClickListener {
            Log.d("myL", "catalogProductAllFilters")
            viewModel.bottomSheetService.show(
                BottomSheetModel(
                    binding = getFilterBinding(viewModel.filterAll.value!!).apply {
                        root.findViewById<ViewGroup>(R.id.catalogProductsFilterBodyContainer)
                            .apply {
                                removeAllViews()
                                viewModel.filterAll.value!!.filters.forEach { filter ->
                                    addView(
                                        getFilterBodyBinding(filter).root
                                    )
                                }
                            }
                    }
                )
            )
        }

        binding.tvListingProductsMore.setOnClickListener {

        }

        if (viewModel.products.value!!.isEmpty()) {
            viewModel.sortModel.editingCompleted.observe(viewLifecycleOwner) {
                viewModel.bottomSheetService.close()
            }

            viewModel.filterAll.observe(viewLifecycleOwner) {
                it?.apply {
                    editingCompleted.observe(viewLifecycleOwner) {
                        viewModel.bottomSheetService.close()
                    }
                }
            }

            viewModel.getCatalogProducts()
        }

        viewModel.filters.observe(viewLifecycleOwner) { filters ->
            binding.filterChips = filters.map { filter ->
                filter.editingCompleted.observe(viewLifecycleOwner) {
                    viewModel.bottomSheetService.close()
                }
                FilterChipModel(
                    filter = filter,
                    onClick = {
                        viewModel.bottomSheetService.show(
                            BottomSheetModel(
                                binding = getFilterBinding(filter)
                            )
                        )
                    },
                    onClickClose = {
                        filter.reset()
                        viewModel.filterAll.value!!.apply()
                    }
                ).apply {
                    filter.anySelected.observe(viewLifecycleOwner) { value ->
                        isSelected.value = value
                    }
                }
            }
        }

        binding.rvListingProducts.adapter = listingProductsAdapter
        viewModel.products.observe(viewLifecycleOwner) {
            listingProductsAdapter.swapData(
                it
            )
        }

        binding.rvProductsWithProducts.adapter = withProductsAdapter
        viewModel.withProducts.observe(viewLifecycleOwner) {
            withProductsAdapter.swapData(it)
        }

        binding.rvProductsWatchedRecently.adapter = watchedRecentlyAdapter
        viewModel.watchedRecently.observe(viewLifecycleOwner) {
            watchedRecentlyAdapter.swapData(it)
        }

        binding.tvProductsWithProductsAll.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                R.id.listing_graph,
                bundleOf(
                    LISTING_ARGUMENT to "С этим товаром покупают"
                )
            )
        }

        binding.tvProductsWatchedRecentlyAll.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                R.id.listing_graph,
                bundleOf(
                    LISTING_ARGUMENT to "Вы недавно смотрели"
                )
            )
        }
    }


    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ProductCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    private fun getFilterBinding(
        filter: IFilter
    ): ViewDataBinding {
        fun getBinding(
            @LayoutRes res: Int
        ) = getBinding(res, filter).apply {
            root.findViewById<ViewGroup>(R.id.catalogProductsFilterBodyContainer).addView(
                getFilterBodyBinding(filter).root
            )
        }

        return when (filter.type) {
            FilterType.PRICE -> getBinding(R.layout.listing_products_filter_price)
            FilterType.RELEASE_FORM -> getBinding(R.layout.listing_products_filter_release_form)
            FilterType.MANUFACTURER -> getBinding(R.layout.listing_products_filter_manufacturer)
            FilterType.DISCOUNTS -> getBinding(R.layout.listing_products_filter_discounts)
            FilterType.NOSOLOGY -> getBinding(R.layout.listing_products_filter_nosology)
            FilterType.SORTING -> TODO()
            FilterType.BRAND -> getBinding(R.layout.listing_products_filter_brand)
            FilterType.COUNTRY -> getBinding(R.layout.listing_products_filter_country)
            FilterType.ACTIVE_SUBSTANCE -> getBinding(R.layout.listing_products_filter_active_substance)
            FilterType.ALL -> DataBindingUtil.inflate<ViewDataBinding?>(
                layoutInflater,
                R.layout.listing_products_filter_all,
                null,
                false
            ).apply {
                setFilterVariable(filter)
            }
        }
    }

    private fun getFilterBodyBinding(
        filter: IFilter
    ) = when (filter.type) {
        FilterType.PRICE -> getBinding(R.layout.listing_products_filter_price_body, filter)
        FilterType.RELEASE_FORM -> getBinding(
            R.layout.listing_products_filter_release_form_body,
            filter
        )

        FilterType.MANUFACTURER -> getBinding(
            R.layout.listing_products_filter_manufacturer_body,
            filter
        )

        FilterType.DISCOUNTS -> getBinding(R.layout.listing_products_filter_discounts_body, filter)
        FilterType.NOSOLOGY -> getBinding(R.layout.listing_products_filter_nosology_body, filter)
        FilterType.SORTING -> TODO()
        FilterType.BRAND -> getBinding(R.layout.listing_products_filter_brand_body, filter)
        FilterType.COUNTRY -> getBinding(R.layout.listing_products_filter_country_body, filter)
        FilterType.ACTIVE_SUBSTANCE -> getBinding(
            R.layout.listing_products_filter_active_substance_body,
            filter
        )

        FilterType.ALL -> throw NotImplementedError("All не имеет своего тела")
    }


    private fun getBinding(
        @LayoutRes res: Int,
        filter: IFilter
    ) = DataBindingUtil.inflate<ViewDataBinding>(
        layoutInflater,
        res,
        null,
        false
    ).apply {
        setFilterVariable(filter)
    }

    private fun ViewDataBinding.setFilterVariable(filter: IFilter) {
        lifecycleOwner = viewLifecycleOwner
        setVariable(BR.listingProductFilterModel, filter)
        setVariable(BR.lifecycle, viewLifecycleOwner)
    }

    private fun onProductsDayCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ProductCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    override fun onResume() {
        super.onResume()
        binding.listingFragmentToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = args.name
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}