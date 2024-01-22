package ru.apteka.legal_documents.presentation.policy_confidentiality

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import ru.apteka.legal_documents.R
import ru.apteka.legal_documents.databinding.PolicyConfidentialityFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Политика конфеденциальности".
 */
@AndroidEntryPoint
class PolicyConfidentialityFragment : BaseFragment<Nothing, PolicyConfidentialityFragmentBinding>() {
    override val layoutId: Int = R.layout.policy_confidentiality_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: PolicyConfidentialityFragmentBinding) {


    }


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.policyConfidentialityToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.legal_documents_policy_confidentiality_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}