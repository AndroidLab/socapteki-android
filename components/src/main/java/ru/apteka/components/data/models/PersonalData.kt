package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Представляет персональные данные пользователя.
 */
@Parcelize
data class PersonalData(
    val fio: String?,
    val date: String?,
    val phone: String?,
    val userMail: UserMail?,
    val sex: Int?,
    val isReceiveReceipts: Boolean
): Parcelable {
    @Parcelize
    data class UserMail(
        val mail: String?,
        val isVerified: Boolean
    ): Parcelable
}