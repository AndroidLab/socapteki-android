package ru.apteka.contacts.data.models


/**
 * Возвращает модель оценки проведения условий труда.
 */
data class ContactsWorkingConditionModel(
    val title: String,
    val onClick: () -> Unit
)
