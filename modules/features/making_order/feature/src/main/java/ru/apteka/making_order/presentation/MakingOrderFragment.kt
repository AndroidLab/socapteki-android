package ru.apteka.making_order.presentation

import android.util.Log
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.choosing_city_api.R as ChoosingCityApiR
import ru.apteka.making_order.databinding.MakingOrderFragmentBinding


/**
 * Представляет фрагмент "Оформление заказа".
 */
@AndroidEntryPoint
class MakingOrderFragment : BaseFragment<MakingOrderViewModel, MakingOrderFragmentBinding>() {

    override val viewModel: MakingOrderViewModel by viewModels()
    override val layoutId: Int = R.layout.making_order_fragment

    override fun onViewBindingInflated(binding: MakingOrderFragmentBinding) {
        binding.viewModel = viewModel

        binding.makingOrderChoosingCity.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ChoosingCityApiR.id.choosing_city_graph
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.makingOrderToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = getString(R.string.making_order_title)
        }
    }

}