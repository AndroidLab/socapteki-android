package ru.apteka.catalog.presentation.subcatalog

import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.data.models.CatalogItem
import ru.apteka.catalog.databinding.SubCatalogFragmentBinding
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.launchAfter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.listing.LISTING_ARGUMENT
import ru.apteka.components.R as ComponentsR
import ru.apteka.listing.R as ListingR


/**
 * Представляет фрагмент "Подкаталог".
 */
@AndroidEntryPoint
class SubCatalogFragment : BaseFragment<SubCatalogViewModel, SubCatalogFragmentBinding>() {

    override val viewModel: SubCatalogViewModel by viewModels()

    override val layoutId: Int = R.layout.sub_catalog_fragment

    private val args: SubCatalogFragmentArgs by navArgs()

    private val subCatalogAdapter by lazy {
        CompositeDelegateAdapter(
            SubCatalogTitleAdapter(),
            SubCatalogAdapter(::onItemClick)
        )
    }

    override fun onViewBindingInflated(binding: SubCatalogFragmentBinding) {
        viewModel.catalogItem = args.catalogItem

        binding.viewModel = viewModel
        binding.rvSubCatalog.adapter = subCatalogAdapter

        viewModel.subCatalogItemsFound.observe(viewLifecycleOwner) {
            subCatalogAdapter.swapData(
                it
            )
        }
    }

    private fun onItemClick(item: CatalogItem) {
        viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
            ListingR.id.listing_graph,
            bundleOf(
                LISTING_ARGUMENT to item.title
            )
        )
    }

    override fun onResume() {
        super.onResume()
        binding.subCatalogToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
            toolbarCustomViewContainer.apply {
                removeAllViews()
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ComponentsR.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        // isLoading = viewModel.isSearchProductsLoading
                        searchText = viewModel.searchText
                        hint = getString(ComponentsR.string.find)
                        isMicIconVisible = false
                        isBarCodeIconVisible = false
                        viewModel.searchText.observe(viewLifecycleOwner) {
                            lifecycleScope.launchAfter(100) {
                                etToolBarSearch.setSelection(etToolBarSearch.text.length)
                            }
                        }
                        keyBoardOpen(etToolBarSearch)
                        searchToolbarSearch.setOnClickListener {
                            etToolBarSearch.setText("")
                        }
                        val deviation = 0.01f
                        val startProgress = 0.1f
                        val middleProgress = 0.25f
                        val endProgress = 0.48f
                        etToolBarSearch.doAfterTextChanged {
                            val progress = searchToolbarSearch.progress
                            if (it.isNullOrEmpty()) {
                                if (progress.equalsWithDeviation(middleProgress, deviation)) {
                                    searchToolbarSearch.playAnimation(0.4f, endProgress)
                                }
                            } else {
                                if (progress.equalsWithDeviation(
                                        startProgress,
                                        deviation
                                    ) || progress.equalsWithDeviation(endProgress, deviation)
                                ) {
                                    searchToolbarSearch.playAnimation(
                                        startProgress,
                                        middleProgress
                                    )
                                }
                            }
                        }
                    }.root
                )
            }
        }
    }
}
