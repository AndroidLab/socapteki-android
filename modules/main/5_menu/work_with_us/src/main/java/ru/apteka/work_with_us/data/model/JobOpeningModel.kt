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
    val experience: String,
    val education: String,
    val workingDay: String,
    val salary: String,
    val responsibilities: List<String>,
    val requirements: List<String>,
    val condition: List<String>,
    val keySkills: List<String>,
    val onItemClick: (JobOpeningModel) -> Unit
): Parcelable
