package ru.apteka.work_with_us.data.model

import java.util.UUID


/**
 * Представляет модель отзыва работника.
 */
data class EmployeeReviewModel(
    val photo: Int,
    val post: String,
    val name: String,
    val desc: String,
    val uuid: UUID = UUID.randomUUID()
)
