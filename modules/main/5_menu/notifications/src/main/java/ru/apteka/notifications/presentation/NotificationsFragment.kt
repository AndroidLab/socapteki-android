package ru.apteka.notifications.presentation

import android.util.Log
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.notifications.R
import ru.apteka.notifications.data.model.NotificationModel
import ru.apteka.notifications.databinding.NotificationsFragmentBinding

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
        Log.d("myL", "12135")
        /*viewModel.navigationManager.generalNavController.navigateWithAnim(
            OrdersFragmentDirections.toOrderDetailsFragment(order)
        )*/
    }

    override fun onResume() {
        super.onResume()
        binding.notificationsToolbar.apply {
            tvToolbarTitle.text = getString(R.string.notifications_title)
        }
    }
}
