package ru.apteka.orders.presentation.orders

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.orders.R
import ru.apteka.orders.databinding.OrdersFragmentBinding

/**
 * Представляет фрагмент "Заказы".
 */
@AndroidEntryPoint
class OrdersFragment : BaseFragment<Nothing, OrdersFragmentBinding>() {

    override val layoutId: Int = R.layout.orders_fragment

    override fun onViewBindingInflated(binding: OrdersFragmentBinding) {

    }
}