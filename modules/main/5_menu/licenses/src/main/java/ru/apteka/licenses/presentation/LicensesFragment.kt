package ru.apteka.licenses.presentation

import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.utils.dp
import ru.apteka.components.data.utils.launchMain
import ru.apteka.components.data.utils.screenHeight
import ru.apteka.components.ui.BaseFragment
import ru.apteka.licenses.R
import ru.apteka.licenses.databinding.LicensesFragmentBinding
import ru.apteka.licenses.databinding.LicensesPdfBinding


/**
 * Представляет фрагмент "Лицензии и соглашения".
 */
@AndroidEntryPoint
class LicensesFragment : BaseFragment<LicensesViewModel, LicensesFragmentBinding>(),
    OnPageChangeListener,
    OnLoadCompleteListener,
    OnPageErrorListener {
    override val viewModel: LicensesViewModel by viewModels()
    override val layoutId: Int = R.layout.licenses_fragment

    private var _behavior: BottomSheetBehavior<View>? = null

    override fun onViewBindingInflated(binding: LicensesFragmentBinding) {
        binding.viewModel = viewModel

        lifecycleScope.launchMain {
            viewModel.documentFlow.collect {
                showBottomSheet(
                    CommonBottomSheetModel(
                        fragmentManager = parentFragmentManager,
                        bottomSheetModel = BottomSheetModel(
                            binding = LicensesPdfBinding.inflate(layoutInflater, null, false).also {
                                it.pdfView.fromAsset("sample.pdf")
                                    .defaultPage(0)
                                    .onPageChange(this@LicensesFragment)
                                    .enableAnnotationRendering(true)
                                    .onLoad(this@LicensesFragment)
                                    .scrollHandle(DefaultScrollHandle(requireContext()))
                                    .spacing(10) // in dp
                                    .onPageError(this@LicensesFragment)
                                    .pageFitPolicy(FitPolicy.BOTH)
                                    .load()
                                it.pdfView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, screenHeight - 75.dp)
                            },
                            useScrollableContainer = false
                        ) { behavior ->
                            _behavior = behavior
                        }
                    )
                )
            }
        }
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        _behavior?.isDraggable = page == 0 || page == 1
    }

    override fun loadComplete(nbPages: Int) {

    }

    override fun onPageError(page: Int, t: Throwable?) {

    }

    override fun onResume() {
        super.onResume()
        binding.licensesToolbar.apply {
            tvToolbarTitle.text = getString(R.string.licenses_title)
        }
    }
}