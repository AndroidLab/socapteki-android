package ru.apteka.home.data.models

import java.util.UUID

/**
 * Представляет карточку заказа на главном экране.
 */
data class OrderCardModel(
    val type: String,
    val status: String,
    val number: String,
    val desc: String,
    val id: UUID = UUID.randomUUID()
)
