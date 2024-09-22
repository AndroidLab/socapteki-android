package ru.apteka.components.data.utils.measure

import io.nacular.measured.units.Measure
import io.nacular.measured.units.Units
import java.util.Locale


/**
 * Преобразует значение в указанные единицы измерения с символом.
 * @param unit Целевая единица измерения.
 * @return Отформатированная строка с указанием единиц измерения.
 * @exception IllegalArgumentException В формате используется "%u", но задана неизвестная единица измерения.
 */
fun <T : Units> Measure<T>.format(unit: T = units): String {
    return "${stringValue(unit)} ${unit.suffix}"
}


/**
 * Преобразует значение в указанные единицы измерения без символа.
 * @param unit Целевая единица измерения.
 * @return Строковое значение без единиц измерения.
 */
fun <T : Units> Measure<T>.stringValue(unit: T = units): String {
    return when (unit) {
        is Money -> String.format(Locale.getDefault(), "%,d", value(unit).toLong())
        else -> throw NotImplementedError("Нет реализации преобразования для единицы измерения $unit")
    }
}

/**
 * Преобразовывает значение в указанные единицы измерения.
 * @param unit Целевая единица измерения.
 * @return Цифровое значение.
 */
fun <T : Units> Measure<T>.value(unit: T = units) =
    (this `as` unit).amount


/**
 * Возвращает объект Measure сформированный из числа.
 * @param amount Значение.
 * @param unit Целевая единица измерения.
 */
fun <T : Units> Measure(amount: Number, unit: T) =
    Measure(amount.toDouble(), unit)

/**
 * Возвращает объект Measure сформированный из строки.
 * @param value Значение.
 * @param unit Целевая единица измерения.
 */
fun <T : Units> Measure(value: String, unit: T) =
    Measure(value.replace(Regex("\\s"), "").toDouble(), unit)