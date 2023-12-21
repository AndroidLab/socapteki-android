package ru.apteka.profile.presentation.profile_remove

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountRemoveService
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.ui.FeatureBaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.ProfileRemoveDialogBinding
import ru.apteka.profile.databinding.ProfileRemoveFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Вопрос удаления аккаунта".
 */
@AndroidEntryPoint
class ProfileRemoveFragment :
    FeatureBaseFragment<ProfileRemoveViewModel, ProfileRemoveFragmentBinding>() {
    override val viewModel: ProfileRemoveViewModel by viewModels()
    override val layoutId: Int = R.layout.profile_remove_fragment

    @Inject
    lateinit var accountRemoveService: AccountRemoveService

    override fun onViewBindingInflated(binding: ProfileRemoveFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbProfileRemoveCancel.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
        }

        binding.mbProfileRemove.setOnClickListener {
            viewModel.profileRemove {
                viewModel.accountsPreferences.account = null
                viewModel.navigationManager.bottomAppBarModel.onItemSelected(ru.apteka.components.R.id.home_graph)
                viewModel.messageNoticeService.showCommonDialog(
                    dialogModel = DialogModel(
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.profile_remove_dialog
                        ) { dialog, binding ->
                            binding as ProfileRemoveDialogBinding
                            binding.mbProfileRemoveDialog.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.profileRemoveToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.profile_remove_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}