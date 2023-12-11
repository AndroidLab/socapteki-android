package ru.apteka.profile.presentation.setting_notifications

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.SettingNotificationsFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Настройка уведомлений".
 */
@AndroidEntryPoint
class SettingNotificationsFragment :
    BaseFragment<SettingNotificationsViewModel, SettingNotificationsFragmentBinding>() {
    override val viewModel: SettingNotificationsViewModel by viewModels()
    override val layoutId: Int = R.layout.setting_notifications_fragment


    override fun onViewBindingInflated(binding: SettingNotificationsFragmentBinding) {
        binding.viewModel = viewModel

    }

    override fun onResume() {
        super.onResume()
        binding.mySubscriptionsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.my_subscriptions_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


}