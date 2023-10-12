package ru.apteka.new_module.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.ui.BaseFragment
import ru.apteka.partners.R
import ru.apteka.partners.databinding.PartnersFragmentBinding


/**
 * Представляет фрагмент "".
 */
@AndroidEntryPoint
class PartnersFragment : BaseFragment<PartnersViewModel, PartnersFragmentBinding>() {

    override val viewModel: PartnersViewModel by viewModels()
    override val layoutId: Int = R.layout.partners_fragment

    override fun onViewBindingInflated(binding: PartnersFragmentBinding) {

    }
}