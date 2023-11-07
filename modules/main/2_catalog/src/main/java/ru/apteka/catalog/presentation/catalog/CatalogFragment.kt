package ru.apteka.catalog.presentation.catalog


import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.data.models.CatalogMenuItem
import ru.apteka.catalog.databinding.CatalogFragmentBinding
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.main_common.ui.MainScreenBaseFragment


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
        nController.navigateWithAnim(
            CatalogFragmentDirections.toCatalogProductsFragment(item.title)
        )
    }

    override fun onResume() {
        super.onResume()
        fillMainScreensToolbar(
            toolbarBinding = binding.catalogToolbar,
            onSearchClick = viewModel.navigationManager.showSearchProduct
        )
        binding.catalogToolbar.tvToolbarTitle.text = getString(R.string.catalog_title)
    }

}