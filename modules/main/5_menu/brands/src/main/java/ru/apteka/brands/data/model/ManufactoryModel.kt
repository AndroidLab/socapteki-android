package ru.apteka.brands.data.model


/**
 * Представляет модель производителя.
 */
class ManufactoryModel(
    val title: String,
    val items: List<LettersItemModel>,
) : LettersCardModel(
    title,
    items
)