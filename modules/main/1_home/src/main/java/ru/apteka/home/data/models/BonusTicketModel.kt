package ru.apteka.home.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.apteka.components.data.models.DiscountModel
import java.util.UUID


/**
 *
 */
@Parcelize
data class BonusTicketModel(
    val image: String,
    val title: String,
    val date: String,
    val discount: DiscountModel,
    val isActivated: Boolean,
    val uuid: UUID = UUID.randomUUID()
) : Parcelable
