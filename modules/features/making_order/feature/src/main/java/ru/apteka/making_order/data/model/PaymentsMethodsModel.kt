package ru.apteka.making_order.data.model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.models.ProductVariantModel
import ru.apteka.components.data.models.SelectableModel
import ru.apteka.making_order.R


/**
 * Представляет модель способа выбора оплаты.
 */
data class PaymentsMethodsModel(
    val _items: List<Item> = listOf(
        Item(PaymentMethod.UPON_RECEIPT, R.string.making_order_payments_methods_upon_receipt),
        Item(PaymentMethod.ONLINE, R.string.making_order_payments_methods_online),
        Item(PaymentMethod.SBP, R.string.making_order_payments_methods_spb),
    ),
    val _onItemSelected: (Item) -> Unit
): SelectableModel<PaymentsMethodsModel.Item>(
    items = _items,
    onItemSelected = _onItemSelected
) {
    data class Item(
        val type: PaymentMethod,
        val title: Int,
    ): SelectableItem()

    enum class PaymentMethod {
        UPON_RECEIPT,
        ONLINE,
        SBP
    }
}
