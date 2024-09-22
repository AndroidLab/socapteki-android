package ru.apteka.making_order.data.model

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.models.DeliveryAddressModel
import ru.apteka.components.data.models.SelectableModel


/**
 * Представляет модель способа выбора доставки.
 */
class DeliveryMethodsModel<T : SelectableModel.SelectableItem>(
    val pickPharmacy: T,
    val yandexDelivery: T,
    val courierDelivery: T,
    onItemSelected: (T?) -> Unit
) : SelectableModel<T>(
    items = listOf(pickPharmacy, yandexDelivery, courierDelivery),
    onItemSelected = onItemSelected
) {

    /**
     * Представляет пункт выбора доставки.
     */
    sealed class Item : SelectableItem() {

        interface IItemDelivery {
            val title: Int
            val price: String
            val address: LiveData<DeliveryAddressModel?>
            val date: LiveData<DeliveryDateModel?>
            val isItemSelected: MutableLiveData<Boolean>
            fun onItemClick()
        }

        /**
         * Тип доставки.
         */
        abstract val deliveryType: DeliveryType


        /**
         * Самовывоз из аптеки.
         */
        class PickPharmacy : Item() {

            override val deliveryType = DeliveryType.PICKUP
        }

        /**
         * Яндекс доставка.
         */
        class YandexDelivery(
            @StringRes override val title: Int,
            override val price: String,
            override val address: LiveData<DeliveryAddressModel?>,
            override val date: LiveData<DeliveryDateModel?>
        ) : Item(), IItemDelivery {

            override val deliveryType = DeliveryType.YANDEX
        }

        /**
         * Яндекс доставка.
         */
        class CourierDelivery(
            @StringRes override val title: Int,
            override val price: String,
            override val address: LiveData<DeliveryAddressModel?>,
            override val date: LiveData<DeliveryDateModel?>
        ) : Item(), IItemDelivery {

            override val deliveryType = DeliveryType.COURIER
        }
    }

}
