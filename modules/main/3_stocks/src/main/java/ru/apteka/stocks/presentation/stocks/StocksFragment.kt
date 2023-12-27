package ru.apteka.stocks.presentation.stocks

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.stocks.R
import ru.apteka.stocks.data.models.StockModel
import ru.apteka.stocks.databinding.StocksFragmentBinding


/**
 * Представляет фрагмент "Акции".
 */
@AndroidEntryPoint
class StocksFragment : BaseFragment<StocksViewModel, StocksFragmentBinding>() {
    override val viewModel: StocksViewModel by viewModels()
    override val layoutId: Int = R.layout.stocks_fragment

    private val stocksAdapter by lazy {
        CompositeDelegateAdapter(
            StocksAdapter(::onStockCardClick)
        )
    }

    override fun onViewBindingInflated(binding: StocksFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvStocks.adapter = stocksAdapter

        viewModel.stocks.observe(viewLifecycleOwner) {
            stocksAdapter.swapData(it)
        }
    }

    private fun onStockCardClick(item: StockModel) {
        viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
            StocksFragmentDirections.toStockDetailsFragment(item)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(true)

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
                            viewModel.onStockSearchTextChange.invoke(it.toString())
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