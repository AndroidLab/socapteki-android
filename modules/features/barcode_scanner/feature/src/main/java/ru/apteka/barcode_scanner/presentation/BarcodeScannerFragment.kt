package ru.apteka.barcode_scanner.presentation

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zbar.ZBarScannerView
import ru.apteka.barcode_scanner.R
import ru.apteka.barcode_scanner.databinding.BarcodeScannerFragmentBinding
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.ui.BaseFragment


/**
 * Представляет фрагмент "Сканирование баркода".
 */
@AndroidEntryPoint
class BarcodeScannerFragment : BaseFragment<BarcodeScannerViewModel, BarcodeScannerFragmentBinding>() {

    override val viewModel: BarcodeScannerViewModel by viewModels()
    override val layoutId: Int = R.layout.barcode_scanner_fragment

    private val scanResultHandler = ZBarScannerView.ResultHandler {
        viewModel.getProductByCode(it.contents)
    }

    override fun onViewBindingInflated(binding: BarcodeScannerFragmentBinding) {
        binding.viewModel = viewModel

        binding.zbar.setResultHandler(scanResultHandler)

        viewModel.product.observe(viewLifecycleOwner) {
            if (it != null) {
                showToast(
                    ToastModel(
                        requireContext(),
                        MessageModel("Product " + it.id)
                    )
                )
                binding.zbar.resumeCameraPreview(scanResultHandler)
            }
        }

        viewModel.isScanError.observe(viewLifecycleOwner) {
            binding.zbar.resumeCameraPreview(scanResultHandler)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.barcodeScannerToolbar.apply {
            toolbar.setNavigationIcon(ru.apteka.components.R.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
        }
        binding.zbar.startCamera()
    }

    override fun onPause() {
        super.onPause()
        binding.zbar.stopCamera()
    }
}