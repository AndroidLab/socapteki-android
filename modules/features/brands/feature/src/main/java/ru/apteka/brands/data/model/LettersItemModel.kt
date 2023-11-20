package ru.apteka.brands.data.model

data class LettersItemModel(
    val item: String,
    val onItemClick: (item: String) -> Unit
)