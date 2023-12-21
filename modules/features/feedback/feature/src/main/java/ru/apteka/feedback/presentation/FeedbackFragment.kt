package ru.apteka.feedback.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.method.LinkMovementMethod
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.utils.getPersonalDataSpan
import ru.apteka.components.ui.BaseFragment
import ru.apteka.feedback.R
import ru.apteka.feedback.databinding.FeedbackDialogSuccessBinding
import ru.apteka.feedback.databinding.FeedbackFragmentBinding
import ru.apteka.feedback_api.api.FEEDBACK_REQUEST_KEY_SUCCESS


/**
 * Представляет фрагмент "Обратная связь".
 */
@AndroidEntryPoint
class FeedbackFragment :
    BaseFragment<FeedbackViewModel, FeedbackFragmentBinding>() {
    override val viewModel: FeedbackViewModel by viewModels()
    override val layoutId: Int = R.layout.feedback_fragment

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
            }
        }

    override fun onViewBindingInflated(binding: FeedbackFragmentBinding) {
        binding.viewModel = viewModel

        binding.etContactsFeedbackReason.setDropDownBackgroundResource(ru.apteka.components.R.color.white)

        binding.mbFeedbackAttachFile.setOnClickListener {
            resultLauncher.launch(
                Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                }
            )
        }

        binding.cbContactsFeedback.apply {
            text = getPersonalDataSpan(requireContext())
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        viewModel.isMessageSendSuccess.observe(viewLifecycleOwner) {
            showCommonDialog(
                commonDialogModel = CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.feedback_dialog_success,
                        ) { dialog, binding ->
                            binding as FeedbackDialogSuccessBinding
                            binding.feedbackDialogSuccessToHome.setOnClickListener {
                                setFragmentResult(FEEDBACK_REQUEST_KEY_SUCCESS, bundleOf())
                                viewModel.navigationManager.generalNavController.popBackStack()
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
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.contactsToolbarFeedback.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.feedback_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}