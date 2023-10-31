package ru.apteka.components.data.utils.measure

import io.nacular.measured.units.Units


/**
 * Представляет единицы измерения для длины.
 * @param suffix Суффикс.
 * @param ratio Коэффициент для преобразования значения.
 */
class Money(suffix: String, ratio: Double = 1.0) : Units(suffix, ratio) {
    companion object {
        val ruble = Money("₽")
    }
}