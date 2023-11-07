package ru.apteka.catalog.presentation.catalog_products

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.catalog.R
import ru.apteka.catalog.data.models.FilterType
import ru.apteka.catalog.data.models.IFilter
import ru.apteka.catalog.data.models.SearchResultModel
import ru.apteka.catalog.databinding.CatalogProductsFragmentBinding
import ru.apteka.catalog.databinding.CatalogProductsSortBinding
import ru.apteka.components.BR
import ru.apteka.components.data.models.FilterChipModel
import ru.apteka.components.data.models.ProductModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogButtonModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.utils.equalsWithDeviation
import ru.apteka.components.data.utils.launchAfter
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.playAnimation
import ru.apteka.components.databinding.SearchToolbarViewBinding
import ru.apteka.components.ui.BaseFragment
import ru.apteka.components.ui.adapters.ProductCardViewAdapter
import ru.apteka.components.ui.delegate_adapter.CompositeDelegateAdapter
import ru.apteka.product_card_api.api.PRODUCT_CARD_ARGUMENT_PRODUCT
import java.util.Locale
import ru.apteka.components.R as ComponentsR
import ru.apteka.product_card_api.R as ProductCardApiR

/**
 * Представляет фрагмент "Товары каталога".
 */
@AndroidEntryPoint
class CatalogProductsFragment :
    BaseFragment<CatalogProductsViewModel, CatalogProductsFragmentBinding>() {

    override val viewModel: CatalogProductsViewModel by viewModels()

    override val layoutId: Int = R.layout.catalog_products_fragment

    //private val _args: CatalogProductsFragmentArgs by navArgs()

    private val catalogProductsAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick
            )
        )
    }

    private val catalogProductsWithProductBuyAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                true
            )
        )
    }

    private val catalogProductsRecentlyWatchedAdapter by lazy {
        CompositeDelegateAdapter(
            ProductCardViewAdapter(
                this,
                ::onProductsCardClick,
                true
            )
        )
    }

    private val productSearchResultAdapter by lazy {
        CompositeDelegateAdapter(
            SearchHistoryHeaderAdapter(),
            SearchResultHeaderAdapter(),
            SearchResultAdapter(::onSearchResultClick)
        )
    }

    override fun onViewBindingInflated(binding: CatalogProductsFragmentBinding) {
        binding.viewModel = viewModel


        binding.catalogProductsSort.setOnClickListener {
            viewModel.bottomSheetService.show(
                CatalogProductsSortBinding.inflate(layoutInflater, null, false).apply {
                    lifecycleOwner = viewLifecycleOwner
                    catalogProductSortModel = viewModel.sortModel
                }
            )
        }
        viewModel.sortModel.editingCompleted.observe(viewLifecycleOwner) {
            viewModel.bottomSheetService.close()
        }


        viewModel.filterAll.observe(viewLifecycleOwner) {
            it?.apply {
                editingCompleted.observe(viewLifecycleOwner) {
                    viewModel.bottomSheetService.close()
                }
            }
        }
        binding.catalogProductAllFilters.setOnClickListener {
            viewModel.bottomSheetService.show(
                getFilterBinding(viewModel.filterAll.value!!).apply {
                    root.findViewById<ViewGroup>(R.id.catalogProductsFilterBodyContainer).apply {
                        removeAllViews()
                        viewModel.filterAll.value!!.filters.forEach { filter ->
                            addView(
                                getFilterBodyBinding(filter).root
                            )
                        }
                    }
                }
            )
        }

        viewModel.filters.observe(viewLifecycleOwner) { filters ->
            binding.filterChips = filters.map { filter ->
                filter.editingCompleted.observe(viewLifecycleOwner) {
                    viewModel.bottomSheetService.close()
                }
                FilterChipModel(
                    text = filter.title,
                    onClick = {
                        viewModel.bottomSheetService.show(
                            getFilterBinding(filter)
                        )
                    },
                    onClickClose = {
                        filter.reset()
                        viewModel.filterAll.value!!.apply()
                    }
                ).apply {
                    filter.anySelected.observe(viewLifecycleOwner) { value ->
                        isSelected.value = value
                    }
                }
            }
        }


        initSearch()
        initCatalogProducts()
        initCatalogProductsWithProductBuy()
        initCatalogProductsRecentlyWatched()
    }

    private fun initCatalogProducts() {
        if (viewModel.catalogItemName != SEARCH_MODE) viewModel.getCatalogProducts()
        binding.rvCatalogProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCatalogProducts.adapter = catalogProductsAdapter
        viewModel.products.observe(viewLifecycleOwner) {
            catalogProductsAdapter.swapData(
                it
            )
        }
    }

    private fun initCatalogProductsWithProductBuy() {
        viewModel.getProductsWithProductBuy()
        binding.catalogProductsWithProductBuy.header.btn.setOnClickListener {

        }
        binding.catalogProductsWithProductBuy.rv.adapter = catalogProductsWithProductBuyAdapter
        viewModel.productsWithProductBuy.observe(viewLifecycleOwner) {
            catalogProductsWithProductBuyAdapter.swapData(
                it
            )
        }
    }

    private fun initCatalogProductsRecentlyWatched() {
        viewModel.getProductsRecentlyWatched()
        binding.catalogProductsRecentlyWatched.header.btn.setOnClickListener {

        }
        binding.catalogProductsRecentlyWatched.rv.adapter = catalogProductsRecentlyWatchedAdapter
        viewModel.productsRecentlyWatched.observe(viewLifecycleOwner) {
            catalogProductsRecentlyWatchedAdapter.swapData(
                it
            )
        }
    }

    private fun initSearch() {
        binding.rvSearchResult.adapter = productSearchResultAdapter
        viewModel.foundResults.observe(viewLifecycleOwner) {
            productSearchResultAdapter.swapData(it)
        }
    }

    private fun onProductsCardClick(product: ProductModel) {
        viewModel.navigationManager.generalNavController.navigateWithAnim(
            ProductCardApiR.id.product_card_graph, bundleOf(
                PRODUCT_CARD_ARGUMENT_PRODUCT to product
            )
        )
    }

    private fun onSearchResultClick(searchResultModel: SearchResultModel) {
        if (viewModel.textQuery.isNotEmpty()) {
            viewModel.searchProductPreferences.setHistoryRequest(viewModel.textQuery)
        }
        if (searchResultModel.type == SearchResultModel.SearchResultType.HISTORY) {
            viewModel.searchText.value = searchResultModel.text
        } else {
            keyBoardClose()
            viewModel.getCatalogProducts()
        }
    }

    private fun getFilterBinding(
        filter: IFilter
    ): ViewDataBinding {
        fun getBinding(
            @LayoutRes res: Int
        ) = getBinding(res, filter).apply {
            root.findViewById<ViewGroup>(R.id.catalogProductsFilterBodyContainer).addView(
                getFilterBodyBinding(filter).root
            )
        }

        return when (filter.type) {
            FilterType.PRICE -> getBinding(R.layout.catalog_products_filter_price)
            FilterType.RELEASE_FORM -> getBinding(R.layout.catalog_products_filter_release_form)
            FilterType.MANUFACTURER -> getBinding(R.layout.catalog_products_filter_manufacturer)
            FilterType.DISCOUNTS -> getBinding(R.layout.catalog_products_filter_discounts)
            FilterType.NOSOLOGY -> getBinding(R.layout.catalog_products_filter_nosology)
            FilterType.SORTING -> TODO()
            FilterType.BRAND -> getBinding(R.layout.catalog_products_filter_brand)
            FilterType.COUNTRY -> getBinding(R.layout.catalog_products_filter_country)
            FilterType.ACTIVE_SUBSTANCE -> getBinding(R.layout.catalog_products_filter_active_substance)
            FilterType.ALL -> DataBindingUtil.inflate<ViewDataBinding?>(
                layoutInflater,
                R.layout.catalog_products_filter_all,
                null,
                false
            ).apply {
                setFilterVariable(filter)
            }
        }
    }

    private fun getFilterBodyBinding(
        filter: IFilter
    ) = when (filter.type) {
        FilterType.PRICE -> getBinding(R.layout.catalog_products_filter_price_body, filter)
        FilterType.RELEASE_FORM -> getBinding(
            R.layout.catalog_products_filter_release_form_body,
            filter
        )

        FilterType.MANUFACTURER -> getBinding(
            R.layout.catalog_products_filter_manufacturer_body,
            filter
        )

        FilterType.DISCOUNTS -> getBinding(R.layout.catalog_products_filter_discounts_body, filter)
        FilterType.NOSOLOGY -> getBinding(R.layout.catalog_products_filter_nosology_body, filter)
        FilterType.SORTING -> TODO()
        FilterType.BRAND -> getBinding(R.layout.catalog_products_filter_brand_body, filter)
        FilterType.COUNTRY -> getBinding(R.layout.catalog_products_filter_country_body, filter)
        FilterType.ACTIVE_SUBSTANCE -> getBinding(
            R.layout.catalog_products_filter_active_substance_body,
            filter
        )

        FilterType.ALL -> throw NotImplementedError("All не имеет своего тела")
    }


    private fun getBinding(
        @LayoutRes res: Int,
        filter: IFilter
    ) = DataBindingUtil.inflate<ViewDataBinding>(
        layoutInflater,
        res,
        null,
        false
    ).apply {
        setFilterVariable(filter)
    }

    private fun ViewDataBinding.setFilterVariable(filter: IFilter) {
        lifecycleOwner = viewLifecycleOwner
        setVariable(BR.catalogProductFilterModel, filter)
        setVariable(BR.lifecycle, viewLifecycleOwner)
    }


    private val mSpeechRecognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(requireContext()).apply {
            setRecognitionListener(micListener)
        }
    }


    private val mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }


    private val micListener = object : RecognitionListener {
        override fun onReadyForSpeech(bundle: Bundle) {
            Log.d("myL", "onReadyForSpeech " + bundle)
        }
        override fun onBeginningOfSpeech() {
            Log.d("myL", "onBeginningOfSpeech")
        }
        override fun onRmsChanged(v: Float) {
            Log.d("myL", "onRmsChanged " + v)
        }
        override fun onBufferReceived(bytes: ByteArray) {
            Log.d("myL", "onBufferReceived " + bytes)
        }
        override fun onEndOfSpeech() {
            Log.d("myL", "onEndOfSpeech")
        }
        override fun onError(i: Int) {
            Log.d("myL", "onError " + i)
        }
        override fun onResults(bundle: Bundle) {
            val matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d("myL", "onResults " + matches!![0])
            //if (matches != null) editText.setText(matches[0])
        }
        override fun onPartialResults(bundle: Bundle) {
            Log.d("myL", "onPartialResults " + bundle)
        }
        override fun onEvent(i: Int, bundle: Bundle) {
            Log.d("myL", "onEvent " + i + " / " + bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.catalogItemName == SEARCH_MODE) {
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
                                Dexter.withContext(mActivity)
                                    .withPermission(Manifest.permission.RECORD_AUDIO)
                                    .withListener(object : PermissionListener {
                                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
                                        }

                                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
                                            showCommonDialog(
                                                CommonDialogModel(
                                                    fragmentManager = childFragmentManager,
                                                    dialogModel = DialogModel(
                                                        message = MessageModel(
                                                            message = ComponentsR.string.access_mic_record
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

                                        override fun onPermissionRationaleShouldBeShown(
                                            permission: PermissionRequest?,
                                            token: PermissionToken?
                                        ) {
                                        }
                                    })
                                    .check()
                            }

                            ivSearchToolbarBarcode.setOnClickListener {
                                Dexter.withContext(mActivity)
                                    .withPermission(Manifest.permission.CAMERA)
                                    .withListener(object : PermissionListener {
                                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                                            viewModel.barCodeScanService.showBarcodeScan()
                                        }

                                        override fun onPermissionDenied(response: PermissionDeniedResponse) {
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

                                        override fun onPermissionRationaleShouldBeShown(
                                            permission: PermissionRequest?,
                                            token: PermissionToken?
                                        ) {
                                        }
                                    })
                                    .check()
                            }

                        }.root
                    )
                }
            }
        } else {
            binding.catalogProductsFragmentToolbar.apply {
                toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
                toolbar.setNavigationOnClickListener {
                    viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
                }
                tvToolbarTitle.text = viewModel.catalogItemName
            }
        }
    }

    companion object {
        const val SEARCH_MODE = "SEARCH_MODE"
    }

}