package ru.apteka.components.data.models


/**
 * Представляет персональные данные пользователя.
 */
data class PersonalData(
    val fio: String,
    val date: String,
    val phone: String,
    val userMail: UserMail,
    val sex: Int,
    val isReceiveReceipts: Boolean
) {
    data class UserMail(
        val mail: String,
        val isVerified: Boolean
    )
}