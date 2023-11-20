package ru.apteka.order_details.presentation

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.order_details.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.order_details.databinding.OrderDetailsFragmentBinding


/**
 * Представляет фрагмент "Детали заказа".
 */
@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<OrderDetailsViewModel, OrderDetailsFragmentBinding>() {
    override val viewModel: OrderDetailsViewModel by viewModels()

    override val layoutId: Int = R.layout.order_details_fragment

    private val _args: OrderDetailsFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: OrderDetailsFragmentBinding) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.order = _args.order
    }

    override fun onResume() {
        super.onResume()
        binding.orderDetailsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = String.format(getString(ComponentsR.string.order_number), _args.order.number)
        }
    }

}