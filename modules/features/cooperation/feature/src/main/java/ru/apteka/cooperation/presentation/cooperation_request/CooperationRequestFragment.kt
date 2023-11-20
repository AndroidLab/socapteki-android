package ru.apteka.cooperation.presentation.cooperation_request

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.cooperation.R
import ru.apteka.cooperation.databinding.CooperationRequestFragmentBinding


/**
 * Представляет фрагмент "Сотрудничество. Заявка на сотрудничество".
 */
@AndroidEntryPoint
class CooperationRequestFragment : BaseFragment<CooperationRequestViewModel, CooperationRequestFragmentBinding>() {
    override val viewModel: CooperationRequestViewModel by viewModels()
    override val layoutId: Int = R.layout.cooperation_request_fragment

    override fun onViewBindingInflated(binding: CooperationRequestFragmentBinding) {
        binding.viewModel = viewModel


    }

    override fun onResume() {
        super.onResume()
        binding.cooperationToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.cooperation_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}