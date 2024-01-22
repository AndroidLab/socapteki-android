package ru.apteka.loyalty_program.presentation

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.launchIO
import ru.apteka.components.data.utils.recyclerAutoScroll
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.loyalty_program.R
import ru.apteka.loyalty_program.databinding.LoyaltyProgramFragmentBinding


/**
 * Представляет фрагмент "Программа лояльности".
 */
@AndroidEntryPoint
class LoyaltyProgramFragment :
    BaseFragment<LoyaltyProgramViewModel, LoyaltyProgramFragmentBinding>() {

    override val viewModel: LoyaltyProgramViewModel by viewModels()
    override val layoutId: Int = R.layout.loyalty_program_fragment

    private val loyaltyProgramAdapter by lazy {
        CompositeDelegateAdapter(
            LoyaltyProgramAdapter()
        )
    }

    override fun onViewBindingInflated(binding: LoyaltyProgramFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvLoyaltyProgram.adapter = loyaltyProgramAdapter

        viewModel.banners.observe(viewLifecycleOwner) {
            loyaltyProgramAdapter.swapData(it)
            if (it.isNotEmpty()) {
                lifecycleScope.launchIO { recyclerAutoScroll(binding.rvLoyaltyProgram) }
            }
        }

        binding.mbLoyaltyProgramAcquaintedOffer.setOnClickListener {

        }

        binding.flLoyaltyProgramRegistration.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        binding.loyaltyProgramToolbar.apply {
            tvToolbarTitle.text = getString(R.string.loyalty_program_title)
        }
    }
}