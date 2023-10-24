package ru.apteka.home.presentation.bonus_program

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.BonusProgramFragmentBinding
import ru.apteka.home.databinding.MySubscriptionsFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Бонусная программа".
 */
@AndroidEntryPoint
class BonusProgramFragment :
    BaseFragment<BonusProgramViewModel, BonusProgramFragmentBinding>() {
    override val viewModel: BonusProgramViewModel by viewModels()
    override val layoutId: Int = R.layout.bonus_program_fragment


    override fun onViewBindingInflated(binding: BonusProgramFragmentBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.bonusProgramHistoryAll.btn.setOnClickListener {

        }

    }

    override fun onResume() {
        super.onResume()
        binding.bonusProgramToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.title = getString(R.string.bonus_program_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }


}