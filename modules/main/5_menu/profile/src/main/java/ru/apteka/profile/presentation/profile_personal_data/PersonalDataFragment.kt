package ru.apteka.profile.presentation.profile_personal_data

import android.app.DatePickerDialog
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.PhoneInputModel.Companion.setPhoneMask
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.profile.R
import ru.apteka.profile.databinding.PersonalDataFragmentBinding
import ru.apteka.profile.databinding.PersonalDataInfoBinding
import ru.apteka.profile.databinding.PersonalDataSexBinding
import ru.apteka.profile.presentation.profile_personal_data_fio.PersonalDataFioFragment
import ru.apteka.profile.presentation.profile_personal_data_mail.PersonalDataMailFragment
import ru.apteka.profile.presentation.profile_personal_data_phone.PersonalDataPhoneFragment
import java.util.Calendar
import java.util.Locale

/**
 * Представляет фрагмент "Личные данные".
 */
@AndroidEntryPoint
class PersonalDataFragment : BaseFragment<PersonalDataViewModel, PersonalDataFragmentBinding>() {
    override val viewModel: PersonalDataViewModel by viewModels()
    override val layoutId: Int = R.layout.personal_data_fragment

    override fun onViewBindingInflated(binding: PersonalDataFragmentBinding) {
        setFragmentResultListener(PersonalDataFioFragment.PERSONAL_DATA_FIO_RESULT) { _, bundle ->
            viewModel.fio.value =
                bundle.getString(PersonalDataFioFragment.PERSONAL_DATA_FIO_RESULT_DATA)
        }
        setFragmentResultListener(PersonalDataPhoneFragment.PERSONAL_DATA_PHONE_RESULT) { _, bundle ->
            viewModel.phone.value =
                bundle.getString(PersonalDataPhoneFragment.PERSONAL_DATA_PHONE_RESULT_DATA)
        }
        setFragmentResultListener(PersonalDataMailFragment.PERSONAL_DATA_MAIL_RESULT) { _, bundle ->
            viewModel._email.value =
                bundle.getParcelable(PersonalDataMailFragment.PERSONAL_DATA_MAIL_RESULT_DATA)
        }

        binding.viewModel = viewModel

        binding.personalDataFio.personalDataItem.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                PersonalDataFragmentDirections.toPersonalDataFioFragment(viewModel.fio.value)
            )
        }

        binding.personalDataDate.apply {
            personalDataInfo.visibility = View.VISIBLE
            personalDataArrow.visibility = View.GONE

            personalDataItem.setOnClickListener {
                val mCalendar: Calendar = Calendar.getInstance()
                val _year: Int = mCalendar[Calendar.YEAR]
                val _month: Int = mCalendar[Calendar.MONTH]
                val _dayOfMonth: Int = mCalendar[Calendar.DAY_OF_MONTH]
                DatePickerDialog(
                    requireContext(),
                    ru.apteka.components.R.style.Theme_Socapteki_DataPicker,
                    { view, year, month, day ->
                        viewModel.date.value =
                            "${String.format(Locale.getDefault(), "%02d", day)}.${
                                String.format(
                                    Locale.getDefault(),
                                    "%02d",
                                    month + 1
                                )
                            }.$year"
                    },
                    _year,
                    _month,
                    _dayOfMonth
                ).show()
            }

            personalDataInfo.setOnClickListener {
                showCommonDialog(
                    CommonDialogModel(
                        fragmentManager = parentFragmentManager,
                        dialogModel = DialogModel(
                            title = R.string.personal_data_date_dialog_info_title,
                            message = MessageModel(
                                message = R.string.personal_data_date_dialog_info_desc
                            ),
                            bodyContent = BodyContentModel(
                                layoutId = R.layout.personal_data_info
                            ) { dialog, binding ->
                                binding as PersonalDataInfoBinding
                                binding.mbPersonalDataInfo.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                        )
                    )
                )
            }
        }

        binding.personalDataPhone.apply {
            personalDataDesc.setPhoneMask("phone")
            personalDataItem.setOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    PersonalDataFragmentDirections.toPersonalDataPhoneFragment()
                )
            }
        }

        binding.personalDataEmail.personalDataItem.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                PersonalDataFragmentDirections.toPersonalDataMailFragment(viewModel._email.value)
            )
        }

        binding.personalDataSex.personalDataItem.setOnClickListener {
            showBottomSheet(
                CommonBottomSheetModel(
                    fragmentManager = parentFragmentManager,
                    bottomSheetModel = BottomSheetModel(
                        layoutId = R.layout.personal_data_sex,
                        onLayoutInflate = { binding, dialog, behavior ->
                            binding as PersonalDataSexBinding
                            binding.model = viewModel.sexModel
                            binding.isLoading = viewModel.sexIsLoading
                            binding.personalDataSexCancel.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    )
                )
            )
        }

        binding.personalDataProfileManagement.personalDataItem.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                PersonalDataFragmentDirections.toProfileManagementFragment()
            )
        }

        binding.personalDataMailQuestion.setOnClickListener {
            showCommonDialog(
                CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        message = MessageModel(
                            message = if (viewModel.isEmailVerified.value!!) R.string.personal_data_mail_question_1 else R.string.personal_data_mail_question_2
                        ),
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.personal_data_info
                        ) { dialog, binding ->
                            binding as PersonalDataInfoBinding
                            binding.mbPersonalDataInfo.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    )
                )
            )
        }

        /*binding.personalDataSave.setOnClickListener {
            viewModel.savePersonalData { personalData ->
                setFragmentResult(
                    PERSONAL_DATA_CHANGE_RESULT, bundleOf(
                        PERSONAL_DATA_CHANGE_RESULT_DATA to personalData
                    )
                )
            }
        }*/
    }

    override fun onResume() {
        super.onResume()
        binding.personalDataToolbar.apply {
            tvToolbarTitle.text = getString(R.string.personal_data)
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}
