package ru.apteka.catalog.presentation.catalog_products

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.data.models.FilterType
import ru.apteka.catalog.data.models.IFilter
import ru.apteka.catalog.databinding.CatalogProductsFragmentBinding
import ru.apteka.catalog.databinding.CatalogProductsSortBinding
import ru.apteka.components.BR
import ru.apteka.components.data.models.FilterChipModel
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.R as ComponentsR

/**
 * Представляет фрагмент "Товары каталога".
 */
@AndroidEntryPoint
class CatalogProductsFragment :
    BaseFragment<CatalogProductsViewModel, CatalogProductsFragmentBinding>() {

    override val viewModel: CatalogProductsViewModel by viewModels()

    override val layoutId: Int = R.layout.catalog_products_fragment

    private val _args: CatalogProductsFragmentArgs by navArgs()

    private val catalogProductsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                viewLifecycleOwner,
                ::onProductsCardClick
            )
        )
    }

    private val catalogProductsWithProductBuyAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                viewLifecycleOwner,
                ::onProductsCardClick
            )
        )
    }

    private val catalogProductsRecentlyWatchedAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                viewLifecycleOwner,
                ::onProductsCardClick
            )
        )
    }

    override fun onViewBindingInflated(binding: CatalogProductsFragmentBinding) {
        binding.viewModel = viewModel


        binding.catalogProductsSort.setOnClickListener {
            viewModel.bottomSheetService.show(
                CatalogProductsSortBinding.inflate(layoutInflater, null, false).apply {
                    lifecycleOwner = viewLifecycleOwner
                    catalogProductSortModel = viewModel.sortModel
                }
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
                getFilterBinding(viewModel.filterAll.value!!).apply {
                    root.findViewById<ViewGroup>(R.id.catalogProductsFilterBodyContainer).apply {
                        removeAllViews()
                        viewModel.filterAll.value!!.filters.forEach { filter ->
                            addView(
                                getFilterBodyBinding(filter).root
                            )
                        }
                    }
                }
            )
        }

        viewModel.filters.observe(viewLifecycleOwner) { filters ->
            binding.filterChips = filters.map { filter ->
                filter.editingCompleted.observe(viewLifecycleOwner) {
                    viewModel.bottomSheetService.close()
                }
                FilterChipModel(
                    text = filter.title,
                    onClick = {
                        viewModel.bottomSheetService.show(
                            getFilterBinding(filter)
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

        initCatalogProducts()
        initCatalogProductsWithProductBuy()
        initCatalogProductsRecentlyWatched()
    }

    private fun initCatalogProducts() {
        binding.rvCatalogProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCatalogProducts.adapter = catalogProductsAdapter
        viewModel.products.observe(viewLifecycleOwner) {
            catalogProductsAdapter.swapData(
                it
            )
        }
    }

    private fun initCatalogProductsWithProductBuy() {
        viewModel.getProductsWithProductBuy()
        binding.catalogProductsWithProductBuy.header.btn.setOnClickListener {

        }
        binding.catalogProductsWithProductBuy.rv.adapter = catalogProductsWithProductBuyAdapter
        viewModel.productsWithProductBuy.observe(viewLifecycleOwner) {
            catalogProductsWithProductBuyAdapter.swapData(
                it
            )
        }
    }

    private fun initCatalogProductsRecentlyWatched() {
        viewModel.getProductsRecentlyWatched()
        binding.catalogProductsRecentlyWatched.header.btn.setOnClickListener {

        }
        binding.catalogProductsRecentlyWatched.rv.adapter = catalogProductsRecentlyWatchedAdapter
        viewModel.productsRecentlyWatched.observe(viewLifecycleOwner) {
            catalogProductsRecentlyWatchedAdapter.swapData(
                it
            )
        }
    }

    private fun onProductsCardClick() {

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
            FilterType.PRICE -> getBinding(R.layout.catalog_products_filter_price)
            FilterType.RELEASE_FORM -> getBinding(R.layout.catalog_products_filter_release_form)
            FilterType.MANUFACTURER -> getBinding(R.layout.catalog_products_filter_manufacturer)
            FilterType.DISCOUNTS -> getBinding(R.layout.catalog_products_filter_discounts)
            FilterType.NOSOLOGY -> getBinding(R.layout.catalog_products_filter_nosology)
            FilterType.SORTING -> TODO()
            FilterType.BRAND -> getBinding(R.layout.catalog_products_filter_brand)
            FilterType.COUNTRY -> getBinding(R.layout.catalog_products_filter_country)
            FilterType.ACTIVE_SUBSTANCE -> getBinding(R.layout.catalog_products_filter_active_substance)
            FilterType.ALL -> DataBindingUtil.inflate<ViewDataBinding?>(
                layoutInflater,
                R.layout.catalog_products_filter_all,
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
        FilterType.PRICE -> getBinding(R.layout.catalog_products_filter_price_body, filter)
        FilterType.RELEASE_FORM -> getBinding(
            R.layout.catalog_products_filter_release_form_body,
            filter
        )

        FilterType.MANUFACTURER -> getBinding(
            R.layout.catalog_products_filter_manufacturer_body,
            filter
        )

        FilterType.DISCOUNTS -> getBinding(R.layout.catalog_products_filter_discounts_body, filter)
        FilterType.NOSOLOGY -> getBinding(R.layout.catalog_products_filter_nosology_body, filter)
        FilterType.SORTING -> TODO()
        FilterType.BRAND -> getBinding(R.layout.catalog_products_filter_brand_body, filter)
        FilterType.COUNTRY -> getBinding(R.layout.catalog_products_filter_country_body, filter)
        FilterType.ACTIVE_SUBSTANCE -> getBinding(
            R.layout.catalog_products_filter_active_substance_body,
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
        setVariable(BR.catalogProductFilterModel, filter)
        setVariable(BR.lifecycle, viewLifecycleOwner)
    }

    override fun onResume() {
        super.onResume()
        binding.catalogProductsFragmentToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
            toolbar.title = _args.catalogItemName
        }
    }


}