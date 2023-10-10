package ru.apteka.home.presentation.home

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.HomeFragmentBinding
import ru.apteka.resources.R as ResourcesR

/**
 * Представляет фрагмент "Главная".
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutId: Int = R.layout.home_fragment

    override fun onViewBindingInflated(binding: HomeFragmentBinding) {
        binding.viewModel = viewModel


    }

    override fun onResume() {
        super.onResume()
        mActivity.supportActionBar!!.apply {
            title = ""
            setLogo(ResourcesR.drawable.logo)
        }
    }

    override fun onStop() {
        super.onStop()
        mActivity.supportActionBar!!.setLogo(null)
    }
}