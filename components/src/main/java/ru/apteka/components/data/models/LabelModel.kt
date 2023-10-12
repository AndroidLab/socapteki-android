package ru.apteka.components.data.models

import androidx.annotation.DrawableRes


/**
 * Представляет модель этикетки.
 * @param text Текст.
 * @param color Цвет.
 */
data class LabelModel(
    val text: String,
    @DrawableRes val color: Int
)
