package ru.apteka.cooperation.presentation.cooperation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.cooperation.R
import ru.apteka.cooperation.databinding.CooperationFragmentBinding
import ru.apteka.cooperation.presentation.cooperation_request.CooperationRequestFragment


/**
 * Представляет фрагмент "Сотрудничество".
 */
@AndroidEntryPoint
class CooperationFragment : BaseFragment<CooperationViewModel, CooperationFragmentBinding>() {

    override val viewModel: CooperationViewModel by viewModels()
    override val layoutId: Int = R.layout.cooperation_fragment

    override fun onViewBindingInflated(binding: CooperationFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbCooperation.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                CooperationFragmentDirections.toCooperationRequestFragment()
            )
        }
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