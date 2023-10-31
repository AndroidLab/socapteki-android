package ru.apteka.order_search.presentation

import android.text.InputType
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.orders.OrdersAdapter
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT_ORDER
import ru.apteka.order_search.R
import ru.apteka.order_search.databinding.OrderSearchFragmentBinding
import ru.apteka.components.R as ComponentsR
import ru.apteka.order_details_api.R as OrderDetailsApiR


/**
 * Представляет фрагмент "Поиск заказов".
 */
@AndroidEntryPoint
class OrderSearchFragment : BaseFragment<OrderSearchViewModel, OrderSearchFragmentBinding>() {

    override val viewModel: OrderSearchViewModel by viewModels()
    override val layoutId: Int = R.layout.order_search_fragment

    private val orderSearchAdapter by lazy {
        CompositeDelegateAdapter(
            OrdersAdapter(::onOrdersClick)
        )
    }

    override fun onViewBindingInflated(binding: OrderSearchFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvOrderSearch.adapter = orderSearchAdapter

        viewModel.foundOrders.observe(viewLifecycleOwner) {
            orderSearchAdapter.swapData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.orderSearchToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.apply {
                layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ComponentsR.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        isLoading = viewModel.isLoading
                        hint = getString(R.string.order_search_hint)
                        etToolBarSearch.inputType = InputType.TYPE_CLASS_NUMBER
                        etToolBarSearch.requestFocus()
                        keyBoardOpen(etToolBarSearch)
                        etToolBarSearch.doAfterTextChanged { text ->
                            this@OrderSearchFragment.viewModel.onOrderSearchTextChange.invoke(text.toString())
                        }
                        ivToolBarSearchClear.setOnClickListener {
                            etToolBarSearch.setText("")
                        }
                    }.root
                )
            }
        }
    }

    private fun onOrdersClick(order: OrderModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            OrderDetailsApiR.id.order_details_graph, bundleOf(
                ORDER_DETAILS_ARGUMENT_ORDER to order
            )
        )
    }

}