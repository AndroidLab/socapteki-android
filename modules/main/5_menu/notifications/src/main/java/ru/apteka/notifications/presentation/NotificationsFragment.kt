package ru.apteka.notifications.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderModel
import ru.apteka.components.data.models.StockModel
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.notifications.R
import ru.apteka.notifications.data.model.NotificationModel
import ru.apteka.notifications.databinding.NotificationsFragmentBinding
import ru.apteka.order_details_api.api.ORDER_DETAILS_ARGUMENT
import ru.apteka.order_details_api.api.STOCK_DETAILS_ARGUMENT
import ru.apteka.order_details_api.R as OrderDetailsApiR
import ru.apteka.stock_details_api.R as StockDetailsApiR

/**
 * Представляет фрагмент "Уведомления".
 */
@AndroidEntryPoint
class NotificationsFragment : BaseFragment<NotificationsViewModel, NotificationsFragmentBinding>() {

    override val viewModel: NotificationsViewModel by viewModels()
    override val layoutId: Int = R.layout.notifications_fragment

    private val notificationAdapter by lazy {
        CompositeDelegateAdapter(
            NotificationAdapter(
                viewLifecycleOwner,
                ::onNotificationClick
            )
        )
    }

    override fun onViewBindingInflated(binding: NotificationsFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvNotification.adapter = notificationAdapter

        viewModel.notificationFiltered.observe(viewLifecycleOwner) {
            notificationAdapter.swapData(it)
        }

        binding.notificationsMarkAsRead.setOnClickListener {
            viewModel.notifications.value!!.onEach {
                it.isRead.value = true
            }
        }
    }

    private fun onNotificationClick(notification: NotificationModel) {
        when(notification.type) {
            NotificationModel.NotificationType.ORDERS -> {
                viewModel.navigationManager.generalNavController.navigateWithAnim(
                    OrderDetailsApiR.id.order_details_graph,
                    bundleOf(
                        ORDER_DETAILS_ARGUMENT to notification.orderOrStock as OrderModel
                    )
                )
            }
            NotificationModel.NotificationType.STOCKS -> {
                viewModel.navigationManager.generalNavController.navigateWithAnim(
                    StockDetailsApiR.id.stock_details_graph,
                    bundleOf(
                        STOCK_DETAILS_ARGUMENT to notification.orderOrStock as StockModel
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.notificationsToolbar.apply {
            tvToolbarTitle.text = getString(R.string.notifications_title)
        }
    }
}
