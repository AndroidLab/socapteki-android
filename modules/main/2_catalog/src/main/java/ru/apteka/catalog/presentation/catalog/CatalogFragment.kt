package ru.apteka.catalog.presentation.catalog


import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.listing_api.R as ListingApiR
import ru.apteka.catalog.data.models.CatalogItem
import ru.apteka.catalog.databinding.CatalogFragmentBinding
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.databinding.ToolbarMenuBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.listing_api.api.LISTING_ARGUMENT


/**
 * Представляет фрагмент "Каталог".
 */
@AndroidEntryPoint
class CatalogFragment : BaseFragment<CatalogViewModel, CatalogFragmentBinding>() {

    override val viewModel: CatalogViewModel by viewModels()

    override val layoutId: Int = R.layout.catalog_fragment

    private val catalogMenuAdapter by lazy {
        CompositeDelegateAdapter(
            CatalogAdapter(::onMenuItemClick)
        )
    }

    override fun onViewBindingInflated(binding: CatalogFragmentBinding) {
        binding.rvCatalog.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCatalog.adapter = catalogMenuAdapter

        viewModel.catalogItems.observe(viewLifecycleOwner) {
            catalogMenuAdapter.swapData(
                it
            )
        }
    }


    private fun onMenuItemClick(item: CatalogItem) {
        viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
            CatalogFragmentDirections.toSubCatalogFragment(item, false)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(true)
        binding.catalogToolbar.apply {
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.addView(
                DataBindingUtil.inflate<ToolbarMenuBinding>(
                    layoutInflater,
                    ru.apteka.components.R.layout.toolbar_menu,
                    null,
                    false
                ).apply {
                    ivMenuSearch.setOnClickListener {
                        viewModel.navigationManager.showSearchProduct.invoke()
                    }
                }.root
            )
        }
        binding.catalogToolbar.tvToolbarTitle.text = getString(R.string.catalog_title)
    }

}