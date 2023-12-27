package ru.apteka.legal_documents.presentation.loyalty_program

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import ru.apteka.licenses.R
import ru.apteka.licenses.databinding.LicensesFragmentBinding
import ru.apteka.licenses.databinding.LoyaltyProgramBonusesFragmentBinding
import ru.apteka.licenses.databinding.LoyaltyProgramFragmentBinding
import ru.apteka.licenses.databinding.PolicyConfidentialityFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Бонусы".
 */
@AndroidEntryPoint
class LoyaltyProgramBonusesFragment : BaseFragment<Nothing, LoyaltyProgramBonusesFragmentBinding>() {
    override val layoutId: Int = R.layout.loyalty_program_bonuses_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: LoyaltyProgramBonusesFragmentBinding) {

    }


    override fun onResume() {
        super.onResume()
        binding.loyaltyProgramBonusesToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_close)
            tvToolbarTitle.text = getString(R.string.loyalty_program_bonuses_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}