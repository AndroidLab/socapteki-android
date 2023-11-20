package ru.apteka.orders.presentation.orders

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.orders.OrdersAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT_ORDER
import ru.apteka.orders.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.orders.databinding.OrdersFragmentBinding
import ru.apteka.order_details_api.R as OrderDetailsApiR
import ru.apteka.order_search_api.R as OrderSearchApiR

/**
 * Представляет фрагмент "Заказы".
 */
@AndroidEntryPoint
class OrdersFragment : MainScreenBaseFragment<OrdersViewModel, OrdersFragmentBinding>() {
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

        binding.mbOrdersToCatalog.setOnClickListener {
            viewModel.navigationManager.onSelectItemId(ru.apteka.main_common.R.id.catalog_graph)
        }

        binding.mbOrdersToStocks.setOnClickListener {
            viewModel.navigationManager.onSelectItemId(ru.apteka.main_common.R.id.stocks_graph)
        }

        viewModel.ordersFiltered.observe(viewLifecycleOwner) {
            ordersAdapter.swapData(it)
        }

    }

    private fun onOrdersClick(order: OrderModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            OrderDetailsApiR.id.order_details_graph, bundleOf(
                ORDER_DETAILS_ARGUMENT_ORDER to order
            )
        )
    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(binding.ordersToolbar) {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                OrderSearchApiR.id.order_search_graph,
            )
        }
        binding.ordersToolbar.tvToolbarTitle.text = getString(R.string.orders_title)
    }

}