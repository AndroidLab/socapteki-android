package ru.apteka.profile.presentation.profile_management

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.ProfileManagementFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Управление профилем".
 */
@AndroidEntryPoint
class ProfileManagementFragment :
    BaseFragment<ProfileManagementViewModel, ProfileManagementFragmentBinding>() {
    override val viewModel: ProfileManagementViewModel by viewModels()
    override val layoutId: Int = R.layout.profile_management_fragment

    @Inject
    lateinit var accountsPreferences: AccountsPreferences

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: ProfileManagementFragmentBinding) {
        binding.viewModel = viewModel

        binding.llProfileManagementLogout.setOnClickListener {
            accountsPreferences.account = null
            navigationManager.bottomAppBarModel.onItemSelected(ru.apteka.components.R.id.home_graph)
        }

        binding.llProfileManagementRemove.setOnClickListener {
            /*showCommonDialog(
                CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        title = R.string.profile_management_remove_dialog_err_title,
                        message = MessageModel(
                            message = R.string.profile_management_remove_dialog_err_desc
                        ),
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.profile_management_remove_dialog_err,
                            onLayoutInflate = { dialog, binding ->
                                binding as ProfileManagementRemoveDialogErrBinding
                                binding.mbProfileManagementRemoveDialogErr.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                        )
                    )
                )
            )*/

            showCommonDialog(
                CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        title = R.string.profile_management_remove_dialog_title,
                        message = MessageModel(
                            message = R.string.profile_management_remove_dialog_desc
                        ),
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.profile_management_remove_dialog,
                            onLayoutInflate = { dialog, binding ->

                            }
                        ),
                        buttonCancel = DialogButtonModel(
                            text = ru.apteka.components.R.string.no
                        ),
                        buttonConfirm = DialogButtonModel(
                            text = ru.apteka.components.R.string.yes
                        ) {
                            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                                ProfileManagementFragmentDirections.toProfileRemoveFragment()
                            )
                        }
                    )
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.profileManagementToolbar.apply {
            tvToolbarTitle.text = getString(R.string.profile_management_title)
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}