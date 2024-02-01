package ru.apteka.choosing_city.presentation

import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.choosing_city.R
import ru.apteka.choosing_city.databinding.ChoosingCityFragmentBinding
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Выбор города".
 */
@AndroidEntryPoint
class ChoosingCityFragment : BaseFragment<ChoosingCityViewModel, ChoosingCityFragmentBinding>() {
    override val viewModel: ChoosingCityViewModel by viewModels()
    override val layoutId: Int = R.layout.choosing_city_fragment

    private val choosingCityAdapter by lazy {
        CompositeDelegateAdapter(
            ChoosingCityAdapter(this),
            ChoosingCityDetectAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: ChoosingCityFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvChoosingCity.adapter = choosingCityAdapter

        viewModel.citiesFilteredMediator.observe(viewLifecycleOwner) {
            choosingCityAdapter.swapData(
                buildList {
                    add(viewModel.cityDetect)
                    addAll(it)
                }
            )
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.choosingCityToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.apply {
                //layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ComponentsR.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        isLoading = viewModel.isLoading
                        viewModel.isLoading.observe(viewLifecycleOwner) {
                            hint = getString(
                                if (it) {
                                    R.string.choosing_city_query_loading_hint
                                } else {
                                    R.string.choosing_city_query_hint
                                }
                            )
                        }
                        searchToolbarSearch.setOnClickListener {
                            etToolBarSearch.setText("")
                        }
                        val deviation = 0.01f
                        val startProgress = 0.1f
                        val middleProgress = 0.25f
                        val endProgress = 0.48f
                        etToolBarSearch.doAfterTextChanged {
                            viewModel.cityQuery.value = it.toString()
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