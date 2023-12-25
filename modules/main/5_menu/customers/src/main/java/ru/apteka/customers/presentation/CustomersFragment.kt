package ru.apteka.customers.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.customers.R
import ru.apteka.customers.databinding.CustomersFragmentBinding


/**
 * Представляет фрагмент "Покупателям".
 */
@AndroidEntryPoint
class CustomersFragment : BaseFragment<CustomersViewModel, CustomersFragmentBinding>() {
    override val viewModel: CustomersViewModel by viewModels()
    override val layoutId: Int = R.layout.customers_fragment


    override fun onViewBindingInflated(binding: CustomersFragmentBinding) {
        binding.viewModel = viewModel

        binding.customersStocks.setOnClickListener {

        }
    }


    override fun onResume() {
        super.onResume()
        binding.customersToolbar.apply {
            tvToolbarTitle.text = getString(R.string.customers_title)
        }
    }
}