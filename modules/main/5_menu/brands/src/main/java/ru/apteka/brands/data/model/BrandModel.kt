package ru.apteka.brands.data.model


/**
 * Представляет модель бренда.
 */
class BrandModel(
    val title: String,
    val items: List<LettersItemModel>,
) : LettersCardModel(
    title,
    items
)