package ru.apteka.making_order.presentation.making_order_address

import android.app.DatePickerDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.DeliveryAddressModel
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.MessageModel
import ru.apteka.components.data.services.message_notice_service.models.ToastModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.services.message_notice_service.showToast
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.R
import ru.apteka.making_order.data.model.DeliveryDateModel
import ru.apteka.making_order.databinding.MakingOrderAddressBinding
import ru.apteka.making_order.databinding.MakingOrderAddressFragmentBinding
import ru.apteka.making_order.databinding.MakingOrderDateBinding
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_DELIVERY
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_DELIVERY_DATA
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

    private val mapView: MapView by lazy {
        binding.makingOrderAddressMapview
    }

    private val map by lazy {
        mapView.mapWindow.map
    }

    override fun onViewBindingInflated(binding: MakingOrderAddressFragmentBinding) {
        MapKitFactory.initialize(requireContext())
        map.move(POSITION)
        showAddressDialog()
    }

    private fun showAddressDialog() {
        showBottomSheet(
            commonBottomSheet = CommonBottomSheetModel(
                fragmentManager = parentFragmentManager,
                bottomSheetModel = BottomSheetModel(
                    layoutId = R.layout.making_order_address,
                    onLayoutInflate = { sheetBinding, dialog, behavior ->
                        sheetBinding as MakingOrderAddressBinding
                        sheetBinding.etMakingOrderAddressStreet.setDropDownBackgroundResource(
                            ComponentsR.color.white
                        )
                        sheetBinding.lifecycleOwner = viewLifecycleOwner
                        sheetBinding.viewModel = viewModel
                        sheetBinding.makingOrderAddressCancel.setOnClickListener {
                            viewModel.navigationManager.generalNavController.popBackStack()
                            dialog.dismiss()
                        }
                        sheetBinding.mbMakingOrderAddress.setOnClickListener {
                            if (viewModel.checkAddressFieldsFilled()) {
                                viewModel.userPreferences.selectedDeliveryAddress =
                                    DeliveryAddressModel(
                                        street = viewModel.selectedStreet.value!!,
                                        home = viewModel.selectedHome.value!!,
                                        floor = viewModel.selectedFloor.value!!,
                                        entrance = viewModel.selectedEntrance.value!!,
                                        flat = viewModel.selectedFlat.value!!,
                                        code = viewModel.selectedCode.value!!
                                    )
                                dialog.dismiss()
                                showDateDialog()
                            } else {
                                viewModel.isAddressFiledError.value = true
                            }
                        }
                    },
                    flagDimBehind = true
                )
            )
        )
    }

    private fun showDateDialog() {
        showBottomSheet(
            commonBottomSheet = CommonBottomSheetModel(
                fragmentManager = parentFragmentManager,
                bottomSheetModel = BottomSheetModel(
                    layoutId = R.layout.making_order_date,
                    onLayoutInflate = { sheetBinding, dialog, behavior ->
                        sheetBinding as MakingOrderDateBinding
                        sheetBinding.lifecycleOwner = viewLifecycleOwner
                        sheetBinding.viewModel = viewModel
                        sheetBinding.makingOrderAddressDataCancel.setOnClickListener {
                            dialog.dismiss()
                            showAddressDialog()
                        }
                        sheetBinding.vMakingOrderAddressDate.setOnClickListener {
                            val mCalendar: Calendar = Calendar.getInstance()
                            val _year: Int = mCalendar[Calendar.YEAR]
                            val _month: Int = mCalendar[Calendar.MONTH]
                            val _dayOfMonth: Int = mCalendar[Calendar.DAY_OF_MONTH]
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
                                        viewModel.selectedDeliveryCalendar.value = calendar
                                    }
                                },
                                _year,
                                _month,
                                _dayOfMonth
                            ).show()
                        }
                        sheetBinding.mbMakingOrderAddressDate.setOnClickListener {
                            if (viewModel.checkDateFieldsFilled()) {
                                val deliveryDate = DeliveryDateModel(
                                    date = viewModel.selectedDeliveryCalendar.value!!.timeInMillis,
                                    time = viewModel.selectedDeliveryTime.value!!
                                )
                                setFragmentResult(
                                    MAKING_ORDER_ARGUMENT_DELIVERY,
                                    bundleOf(
                                        MAKING_ORDER_ARGUMENT_DELIVERY_DATA to deliveryDate
                                    )
                                )
                                dialog.dismiss()
                                viewModel.navigationManager.generalNavController.popBackStack()
                            } else {
                                viewModel.isDateFiledError.value = true
                            }
                        }
                    },
                    flagDimBehind = true
                )
            )
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.makingOrderAddressToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_close)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = viewModel.userPreferences.city?.name ?: "Город не выбран"
        }
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    companion object {
        @JvmStatic
        private val POSITION = CameraPosition(Point(55.753216, 37.619299), 12.0f, 0f, 0f)
    }
}