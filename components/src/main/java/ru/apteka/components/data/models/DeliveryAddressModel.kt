package ru.apteka.components.data.models

/**
 * Представляет моджель с адресом доставки.
 */
data class DeliveryAddressModel(
    val street: String,
    val home: String,
    val floor: String,
    val entrance: String,
    val flat: String,
    val code: String
) {
    val addressFormat
        get() = "ул. $street, дом $home, этаж$floor, подъезд $entrance, кв. $flat"
}
