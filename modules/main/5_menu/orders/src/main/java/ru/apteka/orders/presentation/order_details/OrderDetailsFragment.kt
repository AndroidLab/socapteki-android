package ru.apteka.orders.presentation.order_details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.OrderStatus
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.orders.R
import ru.apteka.orders.databinding.OrderDetailsFragmentBinding
import ru.apteka.orders.databinding.OrderExtendBookingSheetBinding
import ru.apteka.orders.databinding.OrderOpinionSheetBinding
import ru.apteka.orders.presentation.oder_cancel.OrderCancelFragment
import java.util.Calendar
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Детали заказа".
 */
@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<OrderDetailsViewModel, OrderDetailsFragmentBinding>() {
    override val viewModel: OrderDetailsViewModel by viewModels()

    override val layoutId: Int = R.layout.order_details_fragment

    private val _args: OrderDetailsFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: OrderDetailsFragmentBinding) {
        setFragmentResultListener(OrderCancelFragment.ORDER_CANCEL) { _, bundle ->
            viewModel.order.value = viewModel.order.value!!.copy(status = OrderStatus.CANCELED)
        }
        viewModel.order.value = _args.order
        binding.viewModel = viewModel

        binding.orderDetailsDeliveryDate.labelItem.setOnClickListener {
            val mCalendar: Calendar = Calendar.getInstance()
            val _year: Int = mCalendar[Calendar.YEAR]
            val _month: Int = mCalendar[Calendar.MONTH]
            val _dayOfMonth: Int = mCalendar[Calendar.DAY_OF_MONTH]
            DatePickerDialog(
                requireContext(),
                ComponentsR.style.Theme_Socapteki_DataPicker,
                { view, year, month, day ->
                    /*viewModel.date.value =
                        "${String.format("%02d", day)}.${
                            String.format(
                                "%02d",
                                month + 1
                            )
                        }.$year"*/
                },
                _year,
                _month,
                _dayOfMonth
            ).show()
        }

        binding.orderDetailsDeliveryTime.labelItem.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->

                },
                5,
                10,
                true
            ).show()
        }

        binding.tvOrderDetailsExtendBooking.setOnClickListener {
            viewModel.messageService.showCommonDialog(
                dialogModel = DialogModel(
                    bodyContent = BodyContentModel(
                        layoutId = R.layout.order_extend_booking_sheet,
                    ) { dialog, binding ->
                        binding as OrderExtendBookingSheetBinding
                        binding.viewModel = viewModel
                        binding.mbOrderExtendBooking.setOnClickListener {
                            viewModel.extendBooking()
                            dialog.dismiss()
                        }
                    },
                )
            )
        }

        binding.orderDetailsOpinion.setOnClickListener {
            showBottomSheet(
                commonBottomSheet = CommonBottomSheetModel(
                    fragmentManager = parentFragmentManager,
                    bottomSheetModel = BottomSheetModel(
                        layoutId = R.layout.order_opinion_sheet,
                        onLayoutInflate = { binding, bottomSheet, behavior ->
                            binding as OrderOpinionSheetBinding
                            binding.viewModel = viewModel

                            binding.mbOrderOpinionCancel.setOnClickListener {
                                bottomSheet.dismiss()
                            }
                            binding.mbOrderOpinionSend.setOnClickListener {
                                bottomSheet.dismiss()
                                viewModel.sendOpinion {

                                }
                            }
                        }
                    )
                )
            )
        }

        binding.btnOrderDetailsCancel.setOnClickListener {
            viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.navigateWithAnim(
                OrderDetailsFragmentDirections.toOrderCancelFragment(_args.order)
            )
        }

        binding.btnOrderDetailsRepeat.setOnClickListener {

        }

        binding.btnOrderDetailsBuy.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.navigationManager.onBottomAppBarShowed(false)
        binding.orderDetailsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.currentBottomNavControllerLiveData.value!!.popBackStack()
            }
            tvToolbarTitle.text =
                String.format(getString(R.string.order_details_title), _args.order.number)
        }
    }

}