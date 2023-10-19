package ru.apteka.orders.presentation.orders

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.composite_delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.orders.OrdersAdapter
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT_ORDER
import ru.apteka.orders.R
import ru.apteka.order_details_api.R as OrderDetailsApiR
import ru.apteka.orders.databinding.OrdersFragmentBinding

/**
 * Представляет фрагмент "Заказы".
 */
@AndroidEntryPoint
class OrdersFragment : BaseFragment<OrdersViewModel, OrdersFragmentBinding>() {
    override val viewModel: OrdersViewModel by viewModels()

    override val layoutId: Int = R.layout.orders_fragment

    private val ordersAdapter by lazy {
        CompositeDelegateAdapter(
            OrdersAdapter(::onOrdersClick)
        )
    }

    override fun onViewBindingInflated(binding: OrdersFragmentBinding) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvOrders.adapter = ordersAdapter

        viewModel.ordersFiltered.observe(viewLifecycleOwner) {
            ordersAdapter.swapData(it)
        }

    }

    private fun onOrdersClick(order: OrderModel) {
        viewModel.navigationManager.generalNavController.navigate(
            OrderDetailsApiR.id.order_details_graph, bundleOf(
                ORDER_DETAILS_ARGUMENT_ORDER to order
            )
        )
    }

}