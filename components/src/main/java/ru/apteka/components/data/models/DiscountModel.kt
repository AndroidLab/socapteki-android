package ru.apteka.components.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Представляет модель скидки.
 * @param oldPrice Скидка.
 * @param percent Процент.
 */
@Parcelize
data class DiscountModel(
    val oldPrice: String,
    val percent: String
): Parcelable
