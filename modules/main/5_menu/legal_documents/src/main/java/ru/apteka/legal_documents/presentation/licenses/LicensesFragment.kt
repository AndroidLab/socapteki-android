package ru.apteka.legal_documents.presentation.licenses

import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.screenHeight
import ru.apteka.components.ui.BaseFragment
import ru.apteka.legal_documents.R
import ru.apteka.legal_documents.databinding.LicensesFragmentBinding
import ru.apteka.legal_documents.databinding.LicensesPdfBinding
import ru.apteka.legal_documents.presentation.PdfAdapter


/**
 * Представляет фрагмент "Лицензии и соглашения".
 */
@AndroidEntryPoint
class LicensesFragment : BaseFragment<LicensesViewModel, LicensesFragmentBinding>() {
    override val viewModel: LicensesViewModel by viewModels()
    override val layoutId: Int = R.layout.licenses_fragment


    override fun onViewBindingInflated(binding: LicensesFragmentBinding) {
        binding.viewModel = viewModel

        lifecycleScope.launchMain {
            viewModel.documentFlow.collect {
                var prevPage = 2
                var isJumpTo = false
                showBottomSheet(
                    CommonBottomSheetModel(
                        fragmentManager = parentFragmentManager,
                        bottomSheetModel = BottomSheetModel(
                            layoutId = R.layout.licenses_pdf,
                            onLayoutInflate =
                            { binding, dialog, behavior ->
                                binding as LicensesPdfBinding
                                binding.pdfView.fromAsset("sample.pdf")
                                    .defaultPage(0)
                                    .onPageChange { page, pageCount ->
                                        if (!isJumpTo) {
                                            if (page > prevPage) {
                                                binding.pdfAppBar.performHide()
                                            } else {
                                                binding.pdfAppBar.performShow()
                                            }
                                        }
                                        isJumpTo = false
                                        prevPage = page
                                    }
                                    .enableAnnotationRendering(true)
                                    .onLoad { count ->
                                        behavior.isDraggable = false
                                        binding.rvPdf.adapter = PdfAdapter(count) {
                                            isJumpTo = true
                                            binding.pdfView.jumpTo(it, false)
                                        }
                                    }
                                    .scrollHandle(DefaultScrollHandle(requireContext()))
                                    .spacing(10) // in dp
                                    .onPageError { page, t ->

                                    }
                                    .pageFitPolicy(FitPolicy.BOTH)
                                    .load()

                                binding.llLicensesPdfView.layoutParams = FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    screenHeight - 75.dp
                                )
                            },
                            useScrollableContainer = false
                        )
                    )
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.licensesToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.licenses_title)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
        }
    }
}