package ru.apteka.new_module.presentation

import android.content.Context
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.choosing_city.R
import ru.apteka.choosing_city.databinding.ChoosingCityFragmentBinding
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.composite_delegate_adapter.CompositeDelegateAdapter


/**
 * Представляет фрагмент "Выбор города".
 */
@AndroidEntryPoint
class ChoosingCityFragment : BaseFragment<ChoosingCityViewModel, ChoosingCityFragmentBinding>() {
    override val viewModel: ChoosingCityViewModel by viewModels()
    override val layoutId: Int = R.layout.choosing_city_fragment


    private val choosingCityAdapter by lazy {
        CompositeDelegateAdapter(
            ChoosingCityAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: ChoosingCityFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvChoosingCity.adapter = choosingCityAdapter

        viewModel.citiesFilteredMediator.observe(viewLifecycleOwner) {
            choosingCityAdapter.swapData(it)
        }
    }


    override fun onResume() {
        super.onResume()
        mActivity.supportActionBar?.setCustomView(
            DataBindingUtil.inflate<SearchToolbarViewBinding>(
                layoutInflater,
                ru.apteka.components.R.layout.search_toolbar_view,
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
                etToolBarSearch.doAfterTextChanged {
                    viewModel.cityQuery.value = it.toString()
                }
                ivToolBarSearchClear.setOnClickListener {
                    etToolBarSearch.setText("")
                }
            }.root,
            ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.START
            )
        )
    }

}