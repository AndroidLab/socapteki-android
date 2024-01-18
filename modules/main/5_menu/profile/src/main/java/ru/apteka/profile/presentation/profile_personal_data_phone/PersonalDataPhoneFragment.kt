package ru.apteka.profile.presentation.profile_personal_data_phone

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.setSoftInputModeResize
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.PersonalDataPhoneFragmentBinding


/**
 * Представляет фрагмент "Личные данные, изменить номер".
 */
@AndroidEntryPoint
class PersonalDataPhoneFragment :
    BaseFragment<PersonalDataPhoneViewModel, PersonalDataPhoneFragmentBinding>() {
    override val viewModel: PersonalDataPhoneViewModel by viewModels()
    override val layoutId: Int = R.layout.personal_data_phone_fragment

    companion object {
        const val PERSONAL_DATA_PHONE_RESULT = "PERSONAL_DATA_PHONE_RESULT"
        const val PERSONAL_DATA_PHONE_RESULT_DATA = "PERSONAL_DATA_PHONE_RESULT_DATA"
    }

    override fun onViewBindingInflated(binding: PersonalDataPhoneFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbPersonalDataPhoneSave.setOnClickListener {
            viewModel.savePersonalData {
                setFragmentResult(
                    PERSONAL_DATA_PHONE_RESULT,
                    bundleOf(PERSONAL_DATA_PHONE_RESULT_DATA to viewModel.phoneInput.phoneRaw)
                )
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setSoftInputModeResize()
        binding.personalDataPhoneToolbar.apply {
            tvToolbarTitle.text = getString(R.string.personal_data_phone_title)
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}