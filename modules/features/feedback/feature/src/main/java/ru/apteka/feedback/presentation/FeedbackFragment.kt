package ru.apteka.feedback.presentation

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.OpenableColumns
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.getPersonalDataSpan
import ru.apteka.components.data.utils.screenWidth
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.feedback.R
import ru.apteka.feedback.data.FileModel
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
                fun showOpenFileError() {
                    showToast(
                        toastModel = ToastModel(
                            context = requireContext(),
                            message = MessageModel(
                                message = "Ошибка открытия файла"
                            )
                        )
                    )
                }

                val uri = result.data?.data
                if (uri == null) {
                    showOpenFileError()
                } else {
                    val returnCursor =
                        requireContext().contentResolver.query(uri, null, null, null, null)
                    if (returnCursor == null) {
                        showOpenFileError()
                    } else {
                        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
                        returnCursor.moveToFirst()

                        viewModel.files.value = viewModel.files.value!!.plus(
                            FileModel(
                                name = returnCursor.getString(nameIndex),
                                uri = uri
                            ) { file ->
                                viewModel.files.value = viewModel.files.value!!.minus(file)
                            }
                        )
                    }
                }
            }
        }

    private val notificationsAdapter by lazy {
        CompositeDelegateAdapter(
            FeedbackFilesAdapter(
                viewLifecycleOwner
            )
        )
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

        binding.rvFeedbackFiles.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (binding.rvFeedbackFiles.layoutParams.width <= screenWidth) {
                val lp = binding.rvFeedbackFiles.layoutParams
                lp.width = screenWidth + 8.dp - 16.dp * 2
                binding.rvFeedbackFiles.layoutParams = lp
                binding.rvFeedbackFiles.translationX = -8.dp.toFloat()
            }
        }

        binding.rvFeedbackFiles.adapter = notificationsAdapter
        viewModel.files.observe(viewLifecycleOwner) {
            notificationsAdapter.swapData(
                it
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