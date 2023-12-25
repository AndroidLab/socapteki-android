package ru.apteka.profile.presentation.setting_notifications

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.ProfileNotificationsFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Настройка уведомлений".
 */
@AndroidEntryPoint
class SettingNotificationsFragment :
    BaseFragment<SettingNotificationsViewModel, ProfileNotificationsFragmentBinding>() {
    override val viewModel: SettingNotificationsViewModel by viewModels()
    override val layoutId: Int = R.layout.profile_notifications_fragment


    override fun onViewBindingInflated(binding: ProfileNotificationsFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbNotificationsSave.setOnClickListener {
            viewModel.save {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.notificationsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.notifications_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


}