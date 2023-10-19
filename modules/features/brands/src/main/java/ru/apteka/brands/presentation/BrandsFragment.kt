package ru.apteka.brands.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.brands.R
import ru.apteka.brands.databinding.BrandsFragmentBinding
import ru.apteka.components.ui.BaseFragment


/**
 * Представляет фрагмент "Бренды".
 */
@AndroidEntryPoint
class BrandsFragment : BaseFragment<BrandsViewModel, BrandsFragmentBinding>() {

    override val viewModel: BrandsViewModel by viewModels()
    override val layoutId: Int = R.layout.brands_fragment

    override fun onViewBindingInflated(binding: BrandsFragmentBinding) {
        /*binding.btn.setOnClickListener {
            controller.navigate(
                BrandsFragmentDirections.toBrandsSecondFragment()
            )
        }*/
    }
}