package ru.apteka.making_order.presentation.making_order_address

import android.app.DatePickerDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.DeliveryAddressModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.R
import ru.apteka.making_order.data.model.DeliveryDateModel
import ru.apteka.making_order.databinding.MakingOrderAddressFragmentBinding
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_DELIVERY
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_DELIVERY_DATE
import java.util.Calendar
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Оформление заказа, выбор адресса доставки".
 */
@AndroidEntryPoint
class MakingOrderAddressFragment :
    BaseFragment<MakingOrderAddressViewModel, MakingOrderAddressFragmentBinding>() {

    override val viewModel: MakingOrderAddressViewModel by viewModels()
    override val layoutId: Int = R.layout.making_order_address_fragment

    private val _args: MakingOrderAddressFragmentArgs by navArgs()

    override fun onViewBindingInflated(binding: MakingOrderAddressFragmentBinding) {
        binding.viewModel = viewModel
        binding.etMakingOrderAddressStreet.setDropDownBackgroundResource(ComponentsR.color.white)

        binding.vMakingOrderAddressDate.setOnClickListener {
            val mCalendar: Calendar = Calendar.getInstance()
            val _year: Int = mCalendar.get(Calendar.YEAR)
            val _month: Int = mCalendar.get(Calendar.MONTH)
            val _dayOfMonth: Int = mCalendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                requireContext(),
                ru.apteka.components.R.style.Theme_Socapteki_DataPicker,
                { view, year, month, day ->
                    val calendar = Calendar.getInstance().apply {
                        set(year, month, day)
                    }
                    if (calendar.timeInMillis < Calendar.getInstance().timeInMillis) {
                        showToast(
                            ToastModel(
                                context = requireContext(),
                                message = MessageModel(
                                    message = getString(R.string.making_order_address_date_time_error)
                                )
                            )
                        )
                    } else {
                        viewModel._selectedDeliveryDate.value = calendar
                    }
                },
                _year,
                _month,
                _dayOfMonth
            ).show()
        }

        binding.mbMakingOrderAddress.setOnClickListener {
            viewModel.userPreferences.selectedDeliveryAddress = DeliveryAddressModel(
                street = viewModel.selectedStreet.value!!,
                home = viewModel.selectedHome.value!!,
                floor = viewModel.selectedFloor.value!!,
                entrance = viewModel.selectedEntrance.value!!,
                flat = viewModel.selectedFlat.value!!,
                code = viewModel.selectedCode.value!!
            )
            val deliveryDate = DeliveryDateModel(
                date = viewModel._selectedDeliveryDate.value!!.timeInMillis,
                time = viewModel.selectedDeliveryTime.value!!
            )
            setFragmentResult(
                MAKING_ORDER_ARGUMENT_DELIVERY, bundleOf(
                    MAKING_ORDER_ARGUMENT_DELIVERY_DATE to deliveryDate
                )
            )
            viewModel.navigationManager.generalNavController.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.makingOrderAddressToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = getString(_args.deliveryType.title)
        }
    }

}