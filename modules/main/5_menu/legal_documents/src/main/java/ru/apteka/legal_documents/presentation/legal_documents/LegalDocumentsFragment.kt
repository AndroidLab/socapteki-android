package ru.apteka.legal_documents.presentation.legal_documents

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.legal_documents.R
import ru.apteka.legal_documents.databinding.LegalDocumentsFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Юридические документы".
 */
@AndroidEntryPoint
class LegalDocumentsFragment : BaseFragment<Nothing, LegalDocumentsFragmentBinding>() {
    override val layoutId: Int = R.layout.legal_documents_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: LegalDocumentsFragmentBinding) {
        binding.apply {
            legalDocumentsLicenses.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toLicensesFragment()
                )
            }

            legalDocumentsPolicyConfidentiality.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toPolicyConfidentialityFragment()
                )
            }

            legalDocumentsLoyaltyProgram.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toLoyaltyProgramFragmentFragment()
                )
            }

            legalDocumentsUserAgreement.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toUserAgreementFragment()
                )
            }

            legalDocumentsMedicinalProducts.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toMedicinalProductsFragment()
                )
            }

            legalDocumentsPublishingReviews.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toPublishingReviewsFragment()
                )
            }

            legalDocumentsSaleGoods.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toSaleGoodsFragment()
                )
            }

            legalDocumentsReferralProgram.legalDocumentsItem.setOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                    LegalDocumentsFragmentDirections.toReferralProgramBonusesFragment()
                )
            }

            legalDocumentsRequisites.legalDocumentsItem.setOnClickListener {

            }

        }

    }

    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(true)
        binding.legalDocumentsToolbar.apply {
            tvToolbarTitle.text = getString(R.string.legal_documents_title)
        }
    }
}