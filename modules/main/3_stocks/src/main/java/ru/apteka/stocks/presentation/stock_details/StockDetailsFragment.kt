package ru.apteka.stocks.presentation.stock_details

import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.getProductCardViewAdapter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import ru.apteka.stocks.R
import ru.apteka.stocks.databinding.StockDetailsFragmentBinding
import ru.apteka.product_card_api.R as productCardApiR


/**
 * Представляет фрагмент "Детали акции".
 */
@AndroidEntryPoint
class StockDetailsFragment : BaseFragment<StockDetailsViewModel, StockDetailsFragmentBinding>() {
    override val viewModel: StockDetailsViewModel by viewModels()
    override val layoutId: Int = R.layout.stock_details_fragment

    private val stockProductsAdapter by lazy {
        getProductCardViewAdapter(
            this,
            ::onProductsCardClick,
        )
    }

    private val args: StockDetailsFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: StockDetailsFragmentBinding) {
        viewModel.stock.value = args.stock
        binding.viewModel = viewModel

        binding.stockDetailsDesc.doOnLayout {
            //binding.stockDetailsReadCompletely.visibleIf(binding.stockDetailsDesc.countLines() > 5)
        }

        binding.stockDetailsReadCompletely.setOnClickListener {
            if (binding.stockDetailsReadCompletely.text == getString(R.string.stock_details_read_completely)) {
                binding.stockDetailsReadCompletely.text = getString(R.string.stock_details_hide)
                binding.stockDetailsDesc.maxLines = Int.MAX_VALUE
            } else {
                binding.stockDetailsReadCompletely.text =
                    getString(R.string.stock_details_read_completely)
                binding.stockDetailsDesc.maxLines = 5
            }
        }

        binding.stockSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.rvStockProducts.adapter = stockProductsAdapter

        /*viewModel.products.observe(viewLifecycleOwner) {
            stocksAdapter.swapData(it)
        }*/
    }

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            productCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)

        binding.stocksToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_search)
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.apply {
                //layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ru.apteka.components.R.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        isLoading = viewModel.isLoading
                        viewModel.isLoading.observe(viewLifecycleOwner) {
                            hint = getString(R.string.stocks_query_hint)
                        }
                        searchToolbarSearch.setOnClickListener {
                            etToolBarSearch.setText("")
                        }
                        val deviation = 0.01f
                        val startProgress = 0.1f
                        val middleProgress = 0.25f
                        val endProgress = 0.48f
                        etToolBarSearch.doAfterTextChanged {
                            //viewModel.onStockSearchTextChange.invoke(it.toString())
                            val progress = searchToolbarSearch.progress
                            if (it.isNullOrEmpty()) {
                                if (progress.equalsWithDeviation(middleProgress, deviation)) {
                                    searchToolbarSearch.playAnimation(0.4f, endProgress)
                                }
                            } else {
                                if (progress.equalsWithDeviation(
                                        startProgress,
                                        deviation
                                    ) || progress.equalsWithDeviation(endProgress, deviation)
                                ) {
                                    searchToolbarSearch.playAnimation(
                                        startProgress,
                                        middleProgress
                                    )
                                }
                            }
                        }
                    }.root
                )
            }
        }
    }
}