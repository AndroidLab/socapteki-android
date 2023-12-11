package ru.apteka.work_with_us.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Представляет модель вакансии.
 */
@Parcelize
data class JobOpeningModel(
    val name: String,
    val address: String,
    val city: String,
    val onItemClick: (JobOpeningModel) -> Unit
): Parcelable
