package ru.apteka.charity.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.charity.R
import ru.apteka.charity.databinding.CharityFragmentBinding
import ru.apteka.components.ui.BaseFragment


/**
 * Представляет фрагмент "Благотворительность".
 */
@AndroidEntryPoint
class CharityFragment : BaseFragment<CharityViewModel, CharityFragmentBinding>() {

    override val viewModel: CharityViewModel by viewModels()
    override val layoutId: Int = R.layout.charity_fragment

    override fun onViewBindingInflated(binding: CharityFragmentBinding) {
		binding.viewModel = viewModel

        binding.mbCharityFoundation.setOnClickListener {

        }

        binding.mbCharityWorkshop.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        binding.charityToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.charity_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
    }
}