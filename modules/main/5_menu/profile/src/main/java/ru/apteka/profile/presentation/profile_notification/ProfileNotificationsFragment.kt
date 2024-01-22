package ru.apteka.profile.presentation.profile_notification

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.profile.R
import ru.apteka.profile.databinding.ProfileNotificationsFragmentBinding


/**
 * Представляет фрагмент "Личные данные, Уведомления о товарах".
 */
@AndroidEntryPoint
class ProfileNotificationsFragment :
    BaseFragment<ProfileNotificationsViewModel, ProfileNotificationsFragmentBinding>() {
    override val viewModel: ProfileNotificationsViewModel by viewModels()
    override val layoutId: Int = R.layout.profile_notifications_fragment

    private val notificationsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductNotificationsAdapter(
                viewLifecycleOwner
            )
        )
    }

    override fun onViewBindingInflated(binding: ProfileNotificationsFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvNotifications.adapter = notificationsAdapter
        viewModel.notifications.observe(viewLifecycleOwner) {
            notificationsAdapter.swapData(
                it
            )
        }

        binding.rvNotifications.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (binding.rvNotifications.layoutParams.width <= screenWidth) {
                val lp = binding.rvNotifications.layoutParams
                lp.width = screenWidth + 6.dp
                binding.rvNotifications.layoutParams = lp
                binding.rvNotifications.translationX = -6.dp.toFloat()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.profileNotificationToolbar.apply {
            tvToolbarTitle.text = getString(R.string.profile_notifications_title)
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}