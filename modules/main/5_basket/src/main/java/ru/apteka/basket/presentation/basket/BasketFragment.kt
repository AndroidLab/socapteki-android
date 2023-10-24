package ru.apteka.basket.presentation.basket

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.basket.R
import ru.apteka.basket.databinding.BasketFragmentBinding
import ru.apteka.main_common.ui.MainScreenBaseFragment

/**
 * Представляет фрагмент "Корзина".
 */
@AndroidEntryPoint
class BasketFragment : MainScreenBaseFragment<BasketViewModel, BasketFragmentBinding>() {
    override val viewModel: BasketViewModel by viewModels()
    override val layoutId: Int = R.layout.basket_fragment

    override fun onViewBindingInflated(binding: BasketFragmentBinding) {

    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(binding.basketToolbar)
        binding.basketToolbar.toolbar.title = getString(R.string.basket_title)
    }

}