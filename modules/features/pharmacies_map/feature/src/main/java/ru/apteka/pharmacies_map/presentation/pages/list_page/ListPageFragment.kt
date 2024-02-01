package ru.apteka.pharmacies_map.presentation.pages.list_page

import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.PharmacyCardModel
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.pharmacies_map.R
import ru.apteka.pharmacies_map.databinding.ListPageFragmentBinding
import ru.apteka.pharmacies_map.presentation.PharmaciesMapViewModel
import ru.apteka.pharmacies_map.presentation.PharmacyAdapter

/**
 * Представляет фрагмент "Страница со списком".
 */
@AndroidEntryPoint
class ListPageFragment : BaseFragment<PharmaciesMapViewModel, ListPageFragmentBinding>() {

    override val viewModel: PharmaciesMapViewModel by activityViewModels()
    override val layoutId: Int = R.layout.list_page_fragment

    private val pharmacyAdapter by lazy {
        CompositeDelegateAdapter(
            PharmacyAdapter(
                this,
                viewModel.typeInteraction.value!!,
                ::onPharmacyClick,
            )
        )
    }

    override fun onViewBindingInflated(binding: ListPageFragmentBinding) {
        binding.viewModel = viewModel
        binding.rvListPage.adapter = pharmacyAdapter
        viewModel.pharmacies.observe(viewLifecycleOwner) { pharmacies ->
            pharmacyAdapter.swapData(pharmacies)
        }
    }

    private fun onPharmacyClick(pharmacy: PharmacyCardModel) {
        /*moveMap(
            pharmacy.coordinates.first,
            pharmacy.coordinates.second
        )*/
    }
}
