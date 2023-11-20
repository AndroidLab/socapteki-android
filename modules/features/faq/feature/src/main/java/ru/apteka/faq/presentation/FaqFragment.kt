package ru.apteka.faq.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_HOME
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.faq.R
import ru.apteka.faq.databinding.FaqFragmentBinding
import ru.apteka.feedback_api.api.FEEDBACK_REQUEST_KEY_SUCCESS


/**
 * Представляет фрагмент "FAQ".
 */
@AndroidEntryPoint
class FaqFragment : BaseFragment<FaqViewModel, FaqFragmentBinding>() {

    override val viewModel: FaqViewModel by viewModels()
    override val layoutId: Int = R.layout.faq_fragment

    override fun onViewBindingInflated(binding: FaqFragmentBinding) {
        setFragmentResultListener(FEEDBACK_REQUEST_KEY_SUCCESS) { _, _ ->
            setFragmentResult(NAVIGATE_REQUEST_KEY_TO_HOME, bundleOf())
            viewModel.navigationManager.generalNavController.popBackStack()
        }
        binding.viewModel = viewModel

        binding.mbFaq.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.feedback_api.R.id.feedback_graph
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.faqToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.faq_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}