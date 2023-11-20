package ru.apteka.our_partners.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.our_partners.R
import ru.apteka.our_partners.databinding.OurPartnersFragmentBinding


/**
 * Представляет фрагмент "Наши партнеры".
 */
@AndroidEntryPoint
class OurPartnersFragment : BaseFragment<OurPartnersViewModel, OurPartnersFragmentBinding>() {
    override val viewModel: OurPartnersViewModel by viewModels()
    override val layoutId: Int = R.layout.our_partners_fragment

    private val ourPartnerAdapter by lazy {
        CompositeDelegateAdapter(
            OurPartnerAdapter()
        )
    }

    override fun onViewBindingInflated(binding: OurPartnersFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvOurPartner.adapter = ourPartnerAdapter

        viewModel.partnersIcon.observe(viewLifecycleOwner) {
            ourPartnerAdapter.swapData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.ourPartnersToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.our_partners_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}