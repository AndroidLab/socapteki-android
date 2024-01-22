package ru.apteka.listing.presentation

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.BR
import ru.apteka.listing.data.models.FilterChipModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.listing.databinding.ListingFragmentBinding
import ru.apteka.listing.R
import ru.apteka.listing.data.models.FilterType
import ru.apteka.listing.data.models.IFilter
import ru.apteka.listing.databinding.ListingProductsSortBinding
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.product_card_api.R as ProductCardApiR


/**
 * Представляет фрагмент "Листинг".
 */
@AndroidEntryPoint
class ListingFragment : BaseFragment<ListingViewModel, ListingFragmentBinding>() {

    override val viewModel: ListingViewModel by viewModels()
    override val layoutId: Int = R.layout.listing_fragment

    private val _args: ListingFragmentArgs by navArgs()

    private val listingProductsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                false
            )
        )
    }

    override fun onViewBindingInflated(binding: ListingFragmentBinding) {
        binding.viewModel = viewModel
        binding.lifecycle = this

        if (viewModel.products.value!!.isEmpty()) {
            binding.catalogProductsSort.setOnClickListener {
                viewModel.bottomSheetService.show(
                    BottomSheetModel(
                        binding = ListingProductsSortBinding.inflate(layoutInflater, null, false)
                            .apply {
                                lifecycleOwner = viewLifecycleOwner
                                listingProductFilterModel = viewModel.sortModel
                            }
                    )
                )
            }

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
            binding.catalogProductAllFilters.setOnClickListener {
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


    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.listingFragmentToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = _args.name
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}