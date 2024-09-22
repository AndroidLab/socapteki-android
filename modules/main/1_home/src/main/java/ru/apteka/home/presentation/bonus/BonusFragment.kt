package ru.apteka.home.presentation.bonus

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.home.R
import ru.apteka.home.data.models.BonusTicketModel
import ru.apteka.home.databinding.BonusFragmentBinding


/**
 * Представляет фрагмент "Бонусная программа".
 */
@SuppressLint("ResourceType")
@AndroidEntryPoint
class BonusFragment : BaseFragment<BonusViewModel, BonusFragmentBinding>() {

    override val viewModel: BonusViewModel by viewModels()
    override val layoutId: Int = R.layout.bonus_fragment

    private val ticketsAdapter by lazy {
        CompositeDelegateAdapter(
            TicketViewAdapter(
                this,
                ::onTicketCardClick)
        )
    }

    private val bonusNavController by lazy {
        requireActivity().findNavController(R.id.bonus_nav_host)
    }

    override fun onViewBindingInflated(binding: BonusFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvBonusProgramTickets.adapter = ticketsAdapter
        viewModel.tickets.observe(viewLifecycleOwner) {
            ticketsAdapter.swapData(
                it
            )
        }

        binding.tvBonusProgramInviteFriendMore.setOnClickListener {

        }

        binding.bonusProgramHistory.setOnClickListener {
            bonusNavController.navigateWithAnim(
                BonusFragmentDirections.toBonusHistoryFragment()
            )
        }
    }

    private fun onTicketCardClick(ticket: BonusTicketModel) {
        bonusNavController.navigateWithAnim(
            BonusFragmentDirections.toBonusTicketDetailsFragment(ticket)
        )
    }

    override fun onResume() {
        super.onResume()
        binding.bonusProgramToolbar.apply {
            tvToolbarTitle.text = getString(R.string.bonus_history_title)
        }
    }
}
