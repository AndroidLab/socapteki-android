package ru.apteka.basket.presentation.basket

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.basket.R
import ru.apteka.basket.databinding.BasketFragmentBinding

/**
 * Представляет фрагмент "Корзина".
 */
@AndroidEntryPoint
class BasketFragment : BaseFragment<Nothing, BasketFragmentBinding>() {

    override val layoutId: Int = R.layout.basket_fragment

    override fun onViewBindingInflated(binding: BasketFragmentBinding) {

    }
}