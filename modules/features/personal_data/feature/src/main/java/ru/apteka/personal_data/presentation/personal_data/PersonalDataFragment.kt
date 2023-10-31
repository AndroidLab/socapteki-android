package ru.apteka.personal_data.presentation.personal_data

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.CommonDialogFragment
import ru.apteka.components.ui.FeatureBaseFragment
import ru.apteka.personal_data.R
import ru.apteka.personal_data.databinding.PersonalDataFragmentBinding
import ru.apteka.personal_data.databinding.PersonalDataToolbarMenuBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.util.Calendar
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Личные данные".
 */
@AndroidEntryPoint
class PersonalDataFragment :
    FeatureBaseFragment<PersonalDataViewModel, PersonalDataFragmentBinding>() {
    override val viewModel: PersonalDataViewModel by viewModels()
    override val layoutId: Int = R.layout.personal_data_fragment

    override fun onViewBindingInflated(binding: PersonalDataFragmentBinding) {
        binding.viewModel = viewModel

        /*MaskFormatWatcher(
            MaskImpl.createTerminated(
                UnderscoreDigitSlotsParser().parseSlots("__.__.____")
            )
        ).installOn(binding.etPersonalDataDate)*/

        binding.vPersonalDataDate.setOnClickListener {
            val mCalendar: Calendar = Calendar.getInstance()
            val _year: Int = mCalendar.get(Calendar.YEAR)
            val _month: Int = mCalendar.get(Calendar.MONTH)
            val _dayOfMonth: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                requireContext(),
                ru.apteka.components.R.style.Theme_Socapteki_DataPicker,
                { view, year, month, day ->
                    viewModel.date.value = "${String.format("%02d", day)}.${String.format("%02d", month+1)}.$year"
                },
                _year,
                _month,
                _dayOfMonth
            ).show()
        }

        MaskFormatWatcher(
            MaskImpl(
                Slot.copySlotArray(PredefinedSlots.RUS_PHONE_NUMBER).apply {
                    this[3].flags = this[3].flags or Slot.RULE_FORBID_CURSOR_MOVE_LEFT
                },
                true
            )
        ).installOnAndFill(binding.etPersonalDataPhone)

    }

    override fun onResume() {
        super.onResume()
        fillFeatureScreensToolbar(binding.personalDataToolbar)
        binding.personalDataToolbar.toolbar.title = getString(R.string.personal_data)
        binding.personalDataToolbar.toolbarCustomViewContainer.addView(
            PersonalDataToolbarMenuBinding.inflate(
                layoutInflater,
                null,
                false
            ).apply {
                personalDataToolbarMenuDelete.setOnClickListener {
                    viewModel.messageNoticeService.showCommonDialog(
                        dialogModel = DialogModel(
                            title = R.string.personal_data_account_remove_title,
                            message = MessageModel(
                                message = R.string.personal_data_account_remove_message
                            ),
                            buttonCancel = DialogButtonModel(
                                text = ComponentsR.string.no
                            ),
                            buttonConfirm = DialogButtonModel(
                                text = ComponentsR.string.yes,
                            ) {
                                viewModel.navigationManager.generalNavController.navigateWithAnim(PersonalDataFragmentDirections.toQuestionRemoveFragment())
                            }
                        )
                    )
                }
            }.root
        )
    }

}