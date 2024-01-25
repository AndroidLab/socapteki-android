package ru.apteka.making_order.presentation.making_order

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.message_notice_service.showCommonDialog
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.R
import ru.apteka.making_order.data.model.CompletionDataModel
import ru.apteka.making_order.data.model.DeliveryDateModel
import ru.apteka.making_order.data.model.DeliveryTimeModel
import ru.apteka.making_order.databinding.MakingOrderFragmentBinding
import ru.apteka.making_order.databinding.MakingOrderOlectronicReceiptInfoDialogBinding
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_COMPLETION_BACK
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_DELIVERY
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_DELIVERY_DATA
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_RECIPIENTS
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_RECIPIENTS_DATA
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Оформление заказа".
 */
@AndroidEntryPoint
class MakingOrderFragment : BaseFragment<MakingOrderViewModel, MakingOrderFragmentBinding>() {

    override val viewModel: MakingOrderViewModel by viewModels()
    override val layoutId: Int = R.layout.making_order_fragment

    override fun onViewBindingInflated(binding: MakingOrderFragmentBinding) {
        setFragmentResultListener(MAKING_ORDER_ARGUMENT_DELIVERY) { _, bundle ->
            viewModel.selectedDeliveryDate.value = bundle.getParcelable(
                MAKING_ORDER_ARGUMENT_DELIVERY_DATA
            )!!
        }
        setFragmentResultListener(MAKING_ORDER_ARGUMENT_RECIPIENTS) { _, bundle ->
            viewModel.addRecipientOrder(
                bundle.getParcelable(MAKING_ORDER_ARGUMENT_RECIPIENTS_DATA)!!
            )
        }
        setFragmentResultListener(MAKING_ORDER_ARGUMENT_COMPLETION_BACK) { _, bundle ->
            viewModel.navigationManager.generalNavController.popBackStack()
        }

        binding.viewModel = viewModel

        binding.makingOrderRecipientsAdd.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                MakingOrderFragmentDirections.toMakingOrderRecipientsFragment()
            )
        }

        MaskFormatWatcher(
            MaskImpl(
                Slot.copySlotArray(PredefinedSlots.RUS_PHONE_NUMBER),
                true
            )
        ).installOnAndFill(binding.etMakingOrderPhoneNumber)

        binding.makingOrderElectronicReceiptInfo.setOnClickListener {
            showCommonDialog(
                commonDialogModel = CommonDialogModel(
                    fragmentManager = parentFragmentManager,
                    dialogModel = DialogModel(
                        bodyContent = BodyContentModel(
                            layoutId = R.layout.making_order_olectronic_receipt_info_dialog,
                            onLayoutInflate = { dialog, binding ->
                                binding as MakingOrderOlectronicReceiptInfoDialogBinding
                                binding.nmMakingOrderElectronicReceiptInfo.setOnClickListener {
                                    dialog.dismiss()
                                }
                            }
                        )
                    )
                )
            )
        }

        binding.mbMakingOrderComplete.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                MakingOrderFragmentDirections.toMakingOrderCompletionFragment(
                    CompletionDataModel(
                        deliveryDate = viewModel.selectedDeliveryDate.value ?: DeliveryDateModel(
                            date = 1708459096000,
                            time = DeliveryTimeModel.Item(
                                timeFrom = "10:00",
                                timeTo = "18:00"
                            )
                        ),
                        recipients = viewModel.recipients.value!!.map {
                            it.copy().apply {
                                onRemove = null
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
        binding.makingOrderToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = getString(R.string.making_order_title)
        }
    }

}