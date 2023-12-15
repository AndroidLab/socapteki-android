package ru.apteka.personal_data.presentation.question_remove

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.account.AccountRemoveService
import ru.apteka.components.ui.FeatureBaseFragment
import ru.apteka.personal_data.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.personal_data.databinding.QuestionRemoveFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject


/**
 * Представляет фрагмент "Вопрос удаления аккаунта".
 */
@AndroidEntryPoint
class QuestionRemoveFragment :
    FeatureBaseFragment<QuestionRemoveViewModel, QuestionRemoveFragmentBinding>() {
    override val viewModel: QuestionRemoveViewModel by viewModels()
    override val layoutId: Int = R.layout.question_remove_fragment

    @Inject
    lateinit var accountRemoveService: AccountRemoveService

    override fun onViewBindingInflated(binding: QuestionRemoveFragmentBinding) {
        binding.viewModel = viewModel

        binding.questionRemoveCancelButton.setOnClickListener {
            viewModel.navigationManager.generalNavController.popBackStack()
        }

        /*viewModel.codeRaw.observe(viewLifecycleOwner) {
            if (it.length == 4) {
                viewModel.checkCode {
                    accountRemoveService.isAccountRemove.call()
                    viewModel.navigationManager.generalNavController.popBackStack(
                        R.id.personalDataFragment, true
                    )
                }
            }
        }*/

        MaskFormatWatcher(
            MaskImpl.createTerminated(
                UnderscoreDigitSlotsParser().parseSlots("_ _ _ _")
            )
        ).installOn(binding.etQuestionRemoveConfirmCode)
    }

    override fun onResume() {
        super.onResume()
        /*binding.questionRemoveToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_close)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }*/
    }

}