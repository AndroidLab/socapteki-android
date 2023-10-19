package ru.apteka.pharmacies_map.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.pharmacies_map.R
import ru.apteka.pharmacies_map.databinding.PharmaciesMapFragmentBinding


/**
 * Представляет фрагмент "Аптеки на карте".
 */
@AndroidEntryPoint
class PharmaciesMapFragment : BaseFragment<PharmaciesMapViewModel, PharmaciesMapFragmentBinding>() {

    override val viewModel: PharmaciesMapViewModel by viewModels()
    override val layoutId: Int = R.layout.pharmacies_map_fragment

    override fun onViewBindingInflated(binding: PharmaciesMapFragmentBinding) {

    }
}