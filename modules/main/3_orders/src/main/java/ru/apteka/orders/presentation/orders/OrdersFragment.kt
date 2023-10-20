package ru.apteka.orders.presentation.orders

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.orders.OrdersAdapter
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT_ORDER
import ru.apteka.orders.R
import ru.apteka.orders.databinding.OrdersFragmentBinding
import ru.apteka.components.R as ComponentsR
import ru.apteka.order_details_api.R as OrderDetailsApiR
import ru.apteka.order_search_api.R as OrderSearchApiR

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

    override fun onResume() {
        super.onResume()
        binding.ordersToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_menu)
            toolbar.setLogo(ComponentsR.drawable.logo)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.drawerLayout.open()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.addView(
                DataBindingUtil.inflate<ToolbarMenuBinding>(
                    layoutInflater,
                    ComponentsR.layout.toolbar_menu,
                    null,
                    false
                ).apply {
                    ivMenuSearch.setOnClickListener {
                        viewModel.navigationManager.generalNavController.navigate(
                            OrderSearchApiR.id.order_search_graph
                        )
                    }
                    ivMenuDoctor.setOnClickListener {

                    }
                    ivMenuAuth.setOnClickListener {
                        viewModel.navigationManager.navigateToAuthActivity()
                    }
                }.root
            )
        }

    }

}