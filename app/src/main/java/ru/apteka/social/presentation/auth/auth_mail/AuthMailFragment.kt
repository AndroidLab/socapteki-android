package ru.apteka.social.presentation.auth.auth_mail

import android.app.DatePickerDialog
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.ui.BaseFragment
import ru.apteka.social.R
import ru.apteka.social.databinding.AuthMailEReceiptInfoBinding
import ru.apteka.social.databinding.AuthMailFragmentBinding
import java.util.Calendar
import java.util.Locale
import ru.apteka.components.R as ComponentsR
import ru.apteka.profile.R as ProfileR


/**
 * Представляет фрагмент "Авторизация майл".
 */
@AndroidEntryPoint
class AuthMailFragment : BaseFragment<AuthMailViewModel, AuthMailFragmentBinding>() {
    override val viewModel: AuthMailViewModel by viewModels()
    override val layoutId: Int = R.layout.auth_mail_fragment

    override fun onViewBindingInflated(binding: AuthMailFragmentBinding) {
        binding.viewModel = viewModel

        binding.vAuthMailDate.setOnClickListener {
            val mCalendar: Calendar = Calendar.getInstance()
            val _year: Int = mCalendar[Calendar.YEAR]
            val _month: Int = mCalendar[Calendar.MONTH]
            val _dayOfMonth: Int = mCalendar[Calendar.DAY_OF_MONTH]
            DatePickerDialog(
                requireContext(),
                ru.apteka.components.R.style.Theme_Socapteki_DataPicker,
                { view, year, month, day ->
                    viewModel.birthday.value =
                        "${
                            String.format(
                                Locale.getDefault(),
                                "%02d",
                                day,
                            )
                        }.${String.format(Locale.getDefault(), "%02d", month + 1)}.$year"
                },
                _year,
                _month,
                _dayOfMonth
            ).show()
        }

        binding.authMailBirthdayInfo.setOnClickListener {
            showCommonDialog(
                CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        title = ProfileR.string.personal_data_date_dialog_info_title,
                        message = MessageModel(
                            message = ProfileR.string.personal_data_date_dialog_info_desc
                        ),
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.auth_mail_e_receipt_info
                        ) { dialog, binding ->
                            binding as AuthMailEReceiptInfoBinding
                            binding.mbAuthMailReceiptInfo.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    )
                )
            )
        }

        binding.authMailEReceiptQuestion.setOnClickListener {
            showCommonDialog(
                CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        message = MessageModel(
                            message = if (viewModel.isMailVerified.value!!) ProfileR.string.personal_data_mail_question_1 else ProfileR.string.personal_data_mail_question_2
                        ),
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.auth_mail_e_receipt_info
                        ) { dialog, binding ->
                            binding as AuthMailEReceiptInfoBinding
                            binding.mbAuthMailReceiptInfo.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    )
                )
            )
        }

        binding.etAuthMailConfirmCode.doOnTextChanged { text, start, before, count ->
            if (text?.length == 7) {
                viewModel.verifiedMail()
            }
        }

        binding.cbAuthMailEReceipt.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.isReceiveReceipts.value = isChecked
        }

        binding.mbAuthMailSave.setOnClickListener {
            viewModel.save {
                mActivity.finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.authMailToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_close)
            tvToolbarTitle.text = getString(R.string.auth_mail_title)
            toolbar.setNavigationOnClickListener {
                mActivity.finish()
            }
        }
    }
}