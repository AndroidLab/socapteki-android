package ru.apteka.work_with_us.presentation.work_with_us_questionnaire

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_CATALOG
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_HOME
import ru.apteka.components.data.utils.getPersonalDataSpan
import ru.apteka.components.data.utils.setSoftInputModeAdjustPan
import ru.apteka.components.ui.BaseFragment
import ru.apteka.work_with_us.R
import ru.apteka.work_with_us.databinding.WorkWithUsQuestionnaireDialogSuccessBinding
import ru.apteka.work_with_us.databinding.WorkWithUsQuestionnaireFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.util.Calendar
import javax.inject.Inject


/**
 * Представляет фрагмент "Работа у нас, Анкета".
 */
@AndroidEntryPoint
class WorkWithUsQuestionnaireFragment :
    BaseFragment<WorkWithUsQuestionnaireViewModel, WorkWithUsQuestionnaireFragmentBinding>() {

    override val viewModel: WorkWithUsQuestionnaireViewModel by viewModels()
    override val layoutId: Int = R.layout.work_with_us_questionnaire_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: WorkWithUsQuestionnaireFragmentBinding) {
        binding.viewModel = viewModel

        binding.vPersonalDataDate.setOnClickListener {
            val mCalendar: Calendar = Calendar.getInstance()
            val _year: Int = mCalendar.get(Calendar.YEAR)
            val _month: Int = mCalendar.get(Calendar.MONTH)
            val _dayOfMonth: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                requireContext(),
                ru.apteka.components.R.style.Theme_Socapteki_DataPicker,
                { view, year, month, day ->
                    viewModel.birthday.value =
                        "${String.format("%02d", day)}.${String.format("%02d", month + 1)}.$year"
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
        ).installOnAndFill(binding.etWorkWithUsQuestionnairePhone)

        binding.cbWorkWithUsQuestionnairePersonalData.apply {
            text = getPersonalDataSpan(requireContext())
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        viewModel.isQuestionnaireSendSuccess.observe(viewLifecycleOwner) {
            showCommonDialog(
                commonDialogModel = CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.work_with_us_questionnaire_dialog_success,
                        ) { dialog, binding ->
                            binding as WorkWithUsQuestionnaireDialogSuccessBinding
                            binding.questionnaireDialogSuccessToHome.setOnClickListener {
                                setFragmentResult(NAVIGATE_REQUEST_KEY_TO_HOME, bundleOf())
                                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack(
                                    R.id.workWithUsFragment,
                                    true
                                )
                                dialog.dismiss()
                            }
                        }
                    )
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        setSoftInputModeAdjustPan()
        binding.workWithUsQuestionnaireToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.work_with_us_questionnaire_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}