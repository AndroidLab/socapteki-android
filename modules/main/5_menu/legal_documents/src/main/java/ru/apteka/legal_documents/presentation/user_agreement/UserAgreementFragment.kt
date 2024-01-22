package ru.apteka.legal_documents.presentation.user_agreement

import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.components.ui.BaseFragment
import ru.apteka.legal_documents.R
import ru.apteka.legal_documents.databinding.UserAgreementFragmentBinding
import javax.inject.Inject


/**
 * Представляет фрагмент "Пользовательское соглашение".
 */
@AndroidEntryPoint
class UserAgreementFragment : BaseFragment<Nothing, UserAgreementFragmentBinding>() {
    override val layoutId: Int = R.layout.user_agreement_fragment

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onViewBindingInflated(binding: UserAgreementFragmentBinding) {


    }


    override fun onResume() {
        super.onResume()
        navigationManager.onBottomAppBarShowed(false)
        binding.userAgreementToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.legal_documents_user_agreement_title)
            toolbar.setNavigationOnClickListener {
                navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}