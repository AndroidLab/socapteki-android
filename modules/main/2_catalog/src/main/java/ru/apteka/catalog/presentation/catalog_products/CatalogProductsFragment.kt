package ru.apteka.catalog.presentation.catalog_products

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.databinding.CatalogProductsFragmentBinding
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.launchAfter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.listing_api.api.LISTING_ARGUMENT
import ru.apteka.components.R as ComponentsR
import ru.apteka.listing_api.R as ListingApiR


/**
 * Представляет фрагмент "Товары каталога".
 */
@AndroidEntryPoint
class CatalogProductsFragment :
    BaseFragment<CatalogProductsViewModel, CatalogProductsFragmentBinding>() {

    override val viewModel: CatalogProductsViewModel by viewModels()
    override val layoutId: Int = R.layout.catalog_products_fragment

    private val productSearchResultAdapter by lazy {
        CompositeDelegateAdapter(
            SearchHistoryHeaderAdapter(),
            SearchResultHeaderAdapter(),
            SearchResultAdapter(::onSearchResultClick)
        )
    }

    override fun onViewBindingInflated(binding: CatalogProductsFragmentBinding) {
        binding.viewModel = viewModel

        binding.rvSearchResult.adapter = productSearchResultAdapter
        viewModel.foundResults.observe(viewLifecycleOwner) {
            productSearchResultAdapter.swapData(it)
        }
    }

    private fun onSearchResultClick(searchResult: SearchResultModel) {
        if (viewModel.textQuery.isNotEmpty()) {
            viewModel.searchProductPreferences.setHistoryRequest(viewModel.textQuery)
        }
        if (searchResult.type == SearchResultModel.SearchResultType.HISTORY) {
            viewModel.searchText.value = searchResult.text
        } else {
            keyBoardClose()
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ListingApiR.id.listing_graph, bundleOf(
                    LISTING_ARGUMENT to searchResult.text
                )
            )
        }
    }


    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val results = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (results != null) {
                    viewModel.searchText.value = results[0]
                }
            }
        }

    override fun onResume() {
        super.onResume()

        binding.catalogProductsFragmentToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
            toolbarCustomViewContainer.removeAllViews()
            toolbarCustomViewContainer.apply {
                /*layoutParams = Toolbar.LayoutParams(
                    Toolbar.LayoutParams.MATCH_PARENT,
                    Toolbar.LayoutParams.MATCH_PARENT
                )*/
                addView(
                    DataBindingUtil.inflate<SearchToolbarViewBinding>(
                        layoutInflater,
                        ComponentsR.layout.search_toolbar_view,
                        null,
                        false
                    ).apply {
                        lifecycleOwner = viewLifecycleOwner
                        isLoading = viewModel.isSearchProductsLoading
                        searchText = viewModel.searchText
                        hint = getString(R.string.catalog_product_search_hint)
                        isMicIconVisible = true
                        isBarCodeIconVisible = true

                        etToolBarSearch.requestFocus()
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
                            this@CatalogProductsFragment.viewModel.onSearchTextChange.invoke(
                                it.toString()
                            )
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

                        ivSearchToolbarMic.setOnClickListener {
                            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                            i.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            try {
                                resultLauncher.launch(i)
                            } catch (e: Exception) {
                                showToast(
                                    ToastModel(
                                        requireContext(),
                                        MessageModel(e.toString())
                                    )
                                )
                            }

                        }

                        ivSearchToolbarBarcode.setOnClickListener {
                            TedPermission.create()
                                .setPermissionListener(object : PermissionListener {
                                    override fun onPermissionGranted() {
                                        viewModel.navigationManager.generalNavController.navigateWithAnim(
                                            ru.apteka.barcode_scaner_api.R.id.barcode_scanner_graph
                                        )
                                    }

                                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                                        showCommonDialog(
                                            CommonDialogModel(
                                                fragmentManager = childFragmentManager,
                                                dialogModel = DialogModel(
                                                    message = MessageModel(
                                                        message = ComponentsR.string.access_phone_camera
                                                    ),
                                                    buttonCancel = DialogButtonModel(
                                                        text = ComponentsR.string.cancel
                                                    ),
                                                    buttonConfirm = DialogButtonModel(
                                                        text = ComponentsR.string.settings
                                                    ) {
                                                        activity!!.startActivity(
                                                            Intent(
                                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                                Uri.parse("package:" + activity!!.packageName)
                                                            )
                                                        )
                                                    }
                                                )
                                            )
                                        )
                                    }
                                })
                                .setPermissions(Manifest.permission.CAMERA)
                                .check()
                        }

                    }.root
                )
            }
        }

    }

}