package ru.apteka.brands.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.brands.R
import ru.apteka.brands.databinding.BrandsFragmentBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.PagerAdapter
import ru.apteka.brands.presentation.pages.LettersPageFragment


/**
 * Представляет фрагмент "Бренды и производители".
 */
@AndroidEntryPoint
class BrandsFragment : BaseFragment<BrandsViewModel, BrandsFragmentBinding>() {

    override val viewModel: BrandsViewModel by viewModels()
    override val layoutId: Int = R.layout.brands_fragment

    override fun onViewBindingInflated(binding: BrandsFragmentBinding) {
        binding.viewModel = viewModel

        binding.brandsPager.offscreenPageLimit = 1
        binding.brandsPager.adapter = PagerAdapter(
            requireActivity(),
            arrayListOf(
                LettersPageFragment.newInstance(LettersPageFragment.BRAND) as Fragment,
                LettersPageFragment.newInstance(LettersPageFragment.MANUFACTURE) as Fragment,
            )
        )
        TabLayoutMediator(binding.brandsTabLayout, binding.brandsPager) { tab, pos ->
            tab.text = listOf(
                getString(R.string.brands_brands_page),
                getString(R.string.brands_manufactures_page),
            )[pos]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        binding.brandsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}