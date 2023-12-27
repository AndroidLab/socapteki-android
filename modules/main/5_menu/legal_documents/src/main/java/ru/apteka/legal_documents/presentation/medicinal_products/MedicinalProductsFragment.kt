package ru.apteka.legal_documents.presentation.medicinal_products

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.licenses.R
import ru.apteka.licenses.databinding.LicensesFragmentBinding
import ru.apteka.licenses.databinding.LoyaltyProgramFragmentBinding
import ru.apteka.licenses.databinding.MedicinalProductsFragmentBinding
import ru.apteka.licenses.databinding.PolicyConfidentialityFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Правила отпуска лекарственных препаратов".
 */
@AndroidEntryPoint
class MedicinalProductsFragment : BaseFragment<Nothing, MedicinalProductsFragmentBinding>() {
    override val layoutId: Int = R.layout.medicinal_products_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: MedicinalProductsFragmentBinding) {

    }


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.medicinalProductsToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.legal_documents_medicinal_products_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}