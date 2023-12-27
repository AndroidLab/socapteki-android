package ru.apteka.legal_documents.presentation.referral_program

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import ru.apteka.licenses.R
import ru.apteka.licenses.databinding.LegalDocumentsReferralProgramFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Реферальная программа".
 */
@AndroidEntryPoint
class LegalDocumentsReferralProgramFragment : BaseFragment<Nothing, LegalDocumentsReferralProgramFragmentBinding>() {
    override val layoutId: Int = R.layout.legal_documents_referral_program_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: LegalDocumentsReferralProgramFragmentBinding) {

    }


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.referralProgramToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.referral_program_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}