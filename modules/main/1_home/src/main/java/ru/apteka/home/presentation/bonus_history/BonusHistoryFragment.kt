package ru.apteka.home.presentation.bonus_history

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.BonusHistoryFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "История бонусов".
 */
@SuppressLint("ResourceType")
@AndroidEntryPoint
class BonusHistoryFragment : BaseFragment<BonusHistoryViewModel, BonusHistoryFragmentBinding>() {

    override val viewModel: BonusHistoryViewModel by viewModels()
    override val layoutId: Int = R.layout.bonus_history_fragment

    private val bonusNavController by lazy {
        requireActivity().findNavController(R.id.bonus_nav_host)
    }

    override fun onViewBindingInflated(binding: BonusHistoryFragmentBinding) {
        binding.viewModel = viewModel
    }


    override fun onResume() {
        super.onResume()
        binding.bonusHistoryToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.bonus_history_title)
            toolbar.setNavigationOnClickListener {
                bonusNavController.popBackStack()
            }
        }
    }
}
