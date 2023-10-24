package ru.apteka.home.data.models

import java.util.UUID

data class AptekaModel(
    val uuid: UUID,
    val image: String,
    val title: String,
    val desc: String,
    val number: String,
    val isFavorite: Boolean
)