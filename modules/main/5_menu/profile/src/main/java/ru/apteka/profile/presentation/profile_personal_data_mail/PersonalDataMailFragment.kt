package ru.apteka.profile.presentation.profile_personal_data_mail

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.utils.setSoftInputModeAdjustPan
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.PersonalDataMailFragmentBinding

/**
 * Представляет фрагмент "Личные данные, изменить почту".
 */
@AndroidEntryPoint
class PersonalDataMailFragment :
    BaseFragment<PersonalDataMailViewModel, PersonalDataMailFragmentBinding>() {
    override val viewModel: PersonalDataMailViewModel by viewModels()
    override val layoutId: Int = R.layout.personal_data_mail_fragment

    private val args: PersonalDataMailFragmentArgs by navArgs()

    companion object {
        const val PERSONAL_DATA_MAIL_RESULT = "PERSONAL_DATA_MAIL_RESULT"
        const val PERSONAL_DATA_MAIL_RESULT_DATA = "PERSONAL_DATA_MAIL_RESULT_DATA"
    }

    override fun onViewBindingInflated(binding: PersonalDataMailFragmentBinding) {
        viewModel.mail.value = args.mail?.mail ?: ""
        binding.viewModel = viewModel

        binding.mbPersonalDataMailSave.setOnClickListener {
            viewModel.savePersonalData {
                setFragmentResult(
                    PERSONAL_DATA_MAIL_RESULT,
                    bundleOf(
                        PERSONAL_DATA_MAIL_RESULT_DATA to PersonalData.UserMail(
                            mail = viewModel.mail.value!!,
                            isVerified = true
                        )
                    )
                )
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setSoftInputModeAdjustPan()
        binding.personalDataMailToolbar.apply {
            tvToolbarTitle.text = getString(R.string.personal_data_mail_title)
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}
