package ru.apteka.making_order.presentation.making_order_recipients

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.ui.BaseFragment
import ru.apteka.making_order.R
import ru.apteka.making_order.data.model.RecipientModel
import ru.apteka.making_order.databinding.MakingOrderRecipientsFragmentBinding
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_RECIPIENTS
import ru.apteka.making_order_api.api.MAKING_ORDER_ARGUMENT_RECIPIENTS_DATA
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.apteka.components.R as ComponentsR


/**
 * Представляет фрагмент "Оформление заказа, добавление получателей".
 */
@AndroidEntryPoint
class MakingOrderRecipientsFragment :
    BaseFragment<MakingOrderRecipientsViewModel, MakingOrderRecipientsFragmentBinding>() {

    override val viewModel: MakingOrderRecipientsViewModel by viewModels()
    override val layoutId: Int = R.layout.making_order_recipients_fragment

    override fun onViewBindingInflated(binding: MakingOrderRecipientsFragmentBinding) {
        binding.viewModel = viewModel

        MaskFormatWatcher(
            MaskImpl(
                Slot.copySlotArray(PredefinedSlots.RUS_PHONE_NUMBER).apply {
                    this[3].flags = this[3].flags or Slot.RULE_FORBID_CURSOR_MOVE_LEFT
                },
                true
            )
        ).installOnAndFill(binding.etMakingOrderRecipientsPhone)

        binding.tvMakingOrderRecipientsAdd.setOnClickListener {
            setFragmentResult(
                MAKING_ORDER_ARGUMENT_RECIPIENTS, bundleOf(
                    MAKING_ORDER_ARGUMENT_RECIPIENTS_DATA to RecipientModel(
                        fio = viewModel.recipientFio.value!!,
                        phone = viewModel.recipientNumber.value!!
                    )
                )
            )
            viewModel.navigationManager.generalNavController.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.makingOrderRecipientsToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            toolbar.setNavigationOnClickListener {
                viewModel.navigationManager.generalNavController.popBackStack()
            }
            tvToolbarTitle.text = getString(R.string.making_order_recipients_title)
        }
    }

}