package ru.apteka.catalog.presentation.catalog


import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.data.models.CatalogMenuItem
import ru.apteka.catalog.databinding.CatalogFragmentBinding
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Каталог".
 */
@AndroidEntryPoint
class CatalogFragment : MainScreenBaseFragment<CatalogViewModel, CatalogFragmentBinding>() {

    override val viewModel: CatalogViewModel by viewModels()

    override val layoutId: Int = R.layout.catalog_fragment

    private val catalogMenuAdapter by lazy {
        CompositeDelegateAdapter(
            CatalogMenuAdapter(::onMenuItemClick)
        )
    }

    override fun onViewBindingInflated(binding: CatalogFragmentBinding) {
        binding.rvCatalogMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCatalogMenu.adapter = catalogMenuAdapter

        viewModel.catalogs.observe(viewLifecycleOwner) {
            catalogMenuAdapter.swapData(
                it
            )
        }
    }


    private fun onMenuItemClick(item: CatalogMenuItem) {
        nController.navigate(
            CatalogFragmentDirections.toCatalogProductsFragment(item.title)
        )
    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(binding.catalogToolbar)
        binding.catalogToolbar.toolbar.title = getString(R.string.catalog_title)
    }

}