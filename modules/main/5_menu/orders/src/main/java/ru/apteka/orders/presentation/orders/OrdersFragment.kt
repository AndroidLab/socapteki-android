package ru.apteka.orders.presentation.orders

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.orders.R
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
        binding.viewModel = viewModel
        binding.rvOrders.adapter = ordersAdapter

        binding.mbOrdersToCatalog.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.onItemSelected(ru.apteka.components.R.id.catalog_graph)
        }

        binding.mbOrdersToStocks.setOnClickListener {
            viewModel.navigationManager.bottomAppBarModel.onItemSelected(ru.apteka.components.R.id.stocks_graph)
        }

        viewModel.ordersFiltered.observe(viewLifecycleOwner) {
            ordersAdapter.swapData(it)
        }

    }

    private fun onOrdersClick(order: OrderModel) {
        viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
            OrdersFragmentDirections.toOrderDetailsFragment(order)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(true)
        binding.ordersToolbar.apply {
            tvToolbarTitle.text = getString(R.string.orders_title)
        }
    }

}