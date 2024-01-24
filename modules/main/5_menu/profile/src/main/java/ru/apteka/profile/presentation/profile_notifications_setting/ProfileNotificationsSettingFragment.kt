package ru.apteka.profile.presentation.profile_notifications_setting

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.ProfileNotificationsSettingsFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Настройка уведомлений".
 */
@AndroidEntryPoint
class ProfileNotificationsSettingFragment :
    BaseFragment<ProfileNotificationsSettingViewModel, ProfileNotificationsSettingsFragmentBinding>() {
    override val viewModel: ProfileNotificationsSettingViewModel by viewModels()
    override val layoutId: Int = R.layout.profile_notifications_settings_fragment


    override fun onViewBindingInflated(binding: ProfileNotificationsSettingsFragmentBinding) {
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
            tvToolbarTitle.text = getString(R.string.seting_notifications_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


}