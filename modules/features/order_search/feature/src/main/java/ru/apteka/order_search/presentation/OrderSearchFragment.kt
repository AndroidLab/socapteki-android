package ru.apteka.order_search.presentation

import android.text.InputType
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.mainThread
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.composite_delegate_adapter.CompositeDelegateAdapter
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.orders.OrdersAdapter
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT_ORDER
import ru.apteka.order_search.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.order_details_api.R as OrderDetailsApiR
import ru.apteka.order_search.databinding.OrderSearchFragmentBinding


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
        mActivity.supportActionBar?.setCustomView(
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
            }.root,
            ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.START
            )
        )
    }

    private fun onOrdersClick(order: OrderModel) {
        viewModel.navigationManager.generalNavController.navigate(
            OrderDetailsApiR.id.order_details_graph, bundleOf(
                ORDER_DETAILS_ARGUMENT_ORDER to order
            )
        )
    }

}