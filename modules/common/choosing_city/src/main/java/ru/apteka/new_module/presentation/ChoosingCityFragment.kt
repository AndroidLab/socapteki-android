package ru.apteka.new_module.presentation

import android.view.Gravity
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.choosing_city.R
import ru.apteka.choosing_city.databinding.ChoosingCityFragmentBinding
import ru.apteka.choosing_city.databinding.ChoosingCityToolbarViewBinding
import ru.apteka.common.data.composite_delegate_adapter.CompositeDelegateAdapter
import ru.apteka.common.ui.BaseFragment


/**
 * Представляет фрагмент "Выбор города".
 */
@AndroidEntryPoint
class ChoosingCityFragment : BaseFragment<ChoosingCityViewModel, ChoosingCityFragmentBinding>() {
    override val viewModel: ChoosingCityViewModel by viewModels()
    override val layoutId: Int = R.layout.choosing_city_fragment


    private val advertsAdapter by lazy {
        CompositeDelegateAdapter(
            ChoosingCityAdapter(this)
        )
    }

    override fun onViewBindingInflated(binding: ChoosingCityFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvChoosingCity.adapter = advertsAdapter

        viewModel.citiesFilteredMediator.observe(viewLifecycleOwner) {
            advertsAdapter.swapData(it)
        }
    }


    override fun onResume() {
        super.onResume()
        mActivity.supportActionBar?.setCustomView(
            DataBindingUtil.inflate<ChoosingCityToolbarViewBinding>(
                layoutInflater,
                R.layout.choosing_city_toolbar_view,
                null,
                false
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@ChoosingCityFragment.viewModel
            }.root,
            ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.START
            )
        )
    }

    override fun onStop() {
        super.onStop()
        //mActivity.supportActionBar!!.setLogo(null)
    }
}