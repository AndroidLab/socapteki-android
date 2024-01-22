package ru.apteka.profile.presentation.profile_personal_data_fio

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.PersonalData
import ru.apteka.components.data.utils.setSoftInputModeAdjustPan
import ru.apteka.components.data.utils.setSoftInputModeResize
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.PersonalDataFioFragmentBinding
import ru.apteka.profile.databinding.PersonalDataMailFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Представляет фрагмент "Личные данные, изменить ФИО".
 */
@AndroidEntryPoint
class PersonalDataFioFragment :
    BaseFragment<PersonalDataFioViewModel, PersonalDataFioFragmentBinding>() {
    override val viewModel: PersonalDataFioViewModel by viewModels()
    override val layoutId: Int = R.layout.personal_data_fio_fragment

    private val args: PersonalDataFioFragmentArgs by navArgs()

    companion object {
        const val PERSONAL_DATA_FIO_RESULT = "PERSONAL_DATA_FIO_RESULT"
        const val PERSONAL_DATA_FIO_RESULT_DATA = "PERSONAL_DATA_FIO_RESULT_DATA"
    }

    override fun onViewBindingInflated(binding: PersonalDataFioFragmentBinding) {
        viewModel.fio.value = args.fio ?: ""
        binding.viewModel = viewModel

        binding.mbPersonalDataFioSave.setOnClickListener {
            viewModel.savePersonalData {
                setFragmentResult(
                    PERSONAL_DATA_FIO_RESULT,
                    bundleOf(
                        PERSONAL_DATA_FIO_RESULT_DATA to viewModel.fio.value!!
                    )
                )
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setSoftInputModeAdjustPan()
        binding.personalDataFioToolbar.apply {
            tvToolbarTitle.text = getString(R.string.personal_data_fio_title)
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}