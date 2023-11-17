package ru.apteka.making_order.presentation.making_order_completion

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.R
import ru.apteka.making_order.databinding.MakingOrderCompletionFragmentBinding
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_COMPLETION_BACK
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Оформление заказа, завершение".
 */
@AndroidEntryPoint
class MakingOrderCompletionFragment :
    BaseFragment<MakingOrderCompletionViewModel, MakingOrderCompletionFragmentBinding>() {

    override val viewModel: MakingOrderCompletionViewModel by viewModels()
    override val layoutId: Int = R.layout.making_order_completion_fragment

    override fun onViewBindingInflated(binding: MakingOrderCompletionFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbMakingOrderCompletionBack.setOnClickListener {
            viewModel.navigationManager.generalNavController.popBackStack()
        }

        setFragmentResult(MAKING_ORDER_ARGUMENT_COMPLETION_BACK, bundleOf())
    }

    override fun onResume() {
        super.onResume()
        binding.makingOrderCompletionToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = String.format(getString(R.string.making_order_completion_title), "3252462")
        }
    }

}