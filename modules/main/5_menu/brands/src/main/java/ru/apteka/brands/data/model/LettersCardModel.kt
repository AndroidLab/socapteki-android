package ru.apteka.brands.data.model

import kotlin.properties.Delegates

abstract class LettersCardModel {
    var title: String by Delegates.notNull()
    var items: List<LettersItemModel> by Delegates.notNull()
}