package ru.apteka.symptoms_diseases.data.model

import java.util.UUID

/**
 *
 */
class SymptomModel(
    val title: String,
    val id: UUID = UUID.randomUUID()
)