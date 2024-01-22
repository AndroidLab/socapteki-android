package ru.apteka.legal_documents.presentation.loyalty_program

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.licenses.R
import ru.apteka.licenses.databinding.LegalDocumentsLoyaltyProgramFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Правила программы лояльности".
 */
@AndroidEntryPoint
class LoyaltyProgramFragment :
    BaseFragment<Nothing, LegalDocumentsLoyaltyProgramFragmentBinding>() {
    override val layoutId: Int = R.layout.legal_documents_loyalty_program_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: LegalDocumentsLoyaltyProgramFragmentBinding) {
        binding.loyaltyProgramStocks.setOnClickListener {
            navigationManager.bottomAppBarModel.apply {
                onItemSelected(item_3.itemId)
            }
        }

        binding.loyaltyProgramAccrualRulesBonuses.setOnClickListener {
            navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                LoyaltyProgramFragmentDirections.toLoyaltyProgramBonusesFragmentFragment()
            )
        }
    }


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.loyaltyProgramToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.legal_documents_loyalty_program_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}