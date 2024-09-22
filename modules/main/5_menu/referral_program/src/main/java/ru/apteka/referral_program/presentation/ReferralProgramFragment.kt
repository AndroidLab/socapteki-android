package ru.apteka.referral_program.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.referral_program.R
import ru.apteka.referral_program.databinding.ReferralProgramFragmentBinding


/**
 * Представляет фрагмент "Реферальная программа".
 */
@AndroidEntryPoint
class ReferralProgramFragment :
    BaseFragment<ReferralProgramViewModel, ReferralProgramFragmentBinding>() {

    override val viewModel: ReferralProgramViewModel by viewModels()
    override val layoutId: Int = R.layout.referral_program_fragment

    override fun onViewBindingInflated(binding: ReferralProgramFragmentBinding) {
        binding.viewModel = viewModel

        binding.mbReferalProgramAuth.setOnClickListener {
            viewModel.navigationManager.goToAuth()
        }

        binding.mcReferalProgramUrl.setOnClickListener {

        }

        binding.mbReferalProgramShare.setOnClickListener {

        }

        binding.referralProgramOffer.setOnClickListener {

        }

        binding.referralProgramConditions.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        binding.referralProgramToolbar.apply {
            tvToolbarTitle.text = getString(R.string.referral_program_title)
        }
    }
}