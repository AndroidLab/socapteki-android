package ru.apteka.profile.presentation.pharmacies

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.profile.R
import ru.apteka.profile.databinding.PharmaciesFragmentBinding
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Мои аптеки".
 */
@AndroidEntryPoint
class PharmaciesFragment : BaseFragment<PharmaciesViewModel, PharmaciesFragmentBinding>() {
    override val viewModel: PharmaciesViewModel by viewModels()
    override val layoutId: Int = R.layout.pharmacies_fragment

    private val pharmaciesAdapter by lazy {
        CompositeDelegateAdapter(
            PharmaciesAdapter()
        )
    }

    override fun onViewBindingInflated(binding: PharmaciesFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvPharmacies.adapter = pharmaciesAdapter

        viewModel.pharmacies.observe(viewLifecycleOwner) {
            pharmaciesAdapter.swapData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.pharmaciesToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.pharmacies_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }

}