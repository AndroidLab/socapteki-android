package ru.apteka.brands.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.brands.R
import ru.apteka.brands.databinding.BrandsFragmentBinding
import ru.apteka.brands.databinding.BrandsSecondFragmentBinding
import ru.apteka.common.ui.BaseFragment


/**
 * Представляет фрагмент "Бренды второй экран".
 */
@AndroidEntryPoint
class BrandsSecondFragment : BaseFragment<Nothing, BrandsSecondFragmentBinding>() {

    override val layoutId: Int = R.layout.brands_second_fragment

    override fun onViewBindingInflated(binding: BrandsSecondFragmentBinding) {

    }
}