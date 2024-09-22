package ru.apteka.order_details.presentation.order_cancel

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.ui.BaseFragment
import ru.apteka.order_details.R
import ru.apteka.order_details.databinding.OrderCancelFragmentBinding
import ru.apteka.order_details.databinding.OrderCancelSheetBinding
import ru.apteka.components.R as ComponentsR

/**
 * Представляет фрагмент "Отмена заказа".
 */
@AndroidEntryPoint
class OrderCancelFragment : BaseFragment<OrderCancelViewModel, OrderCancelFragmentBinding>() {
    override val viewModel: OrderCancelViewModel by viewModels()

    override val layoutId: Int = R.layout.order_cancel_fragment

    private val args: OrderCancelFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: OrderCancelFragmentBinding) {
        viewModel.order.value = args.order
        binding.viewModel = viewModel

        binding.btnOrderCancel.setOnClickListener {
            showBottomSheet(
                commonBottomSheet = CommonBottomSheetModel(
                    fragmentManager = parentFragmentManager,
                    bottomSheetModel = BottomSheetModel(
                        layoutId = R.layout.order_cancel_sheet,
                        onLayoutInflate = { binding, bottomSheet, behavior ->
                            binding as OrderCancelSheetBinding
                            binding.viewModel = viewModel

                            binding.orderDetailsCancelSheetNotCancel.setOnClickListener {
                                bottomSheet.dismiss()
                            }
                            binding.orderDetailsCancelSheetCancel.setOnClickListener {
                                bottomSheet.dismiss()
                                viewModel.cancelOrder {
                                    setFragmentResult(
                                        ORDER_CANCEL,
                                        bundleOf()
                                    )
                                    viewModel.navigationManager.generalNavController.popBackStack()
                                }
                            }
                        }
                    )
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.orderCancelToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_close)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = getString(R.string.order_cancel_title)
        }
    }

    companion object {
        const val ORDER_CANCEL = "ORDER_CANCEL"
    }
}