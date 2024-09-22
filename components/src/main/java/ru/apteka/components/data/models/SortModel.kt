package ru.apteka.components.data.models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.apteka.components.data.utils.ScopedLiveData
import ru.apteka.components.data.utils.single_live_event.SingleLiveEvent
import ru.apteka.components.R


/**
 * Представляет модель для сортировки.
 */
class SortModel {
    private var _popular = true
    private var _cheaper = false
    private var _expensive = false
    private var _stock = false
    private var _discount = false

    /**
     * Возвращает флаг 'популярные'.
     */
    val popular = MutableLiveData(_popular)

    /**
     * Возвращает флаг 'дешевле'.
     */
    val cheaper = MutableLiveData(_cheaper)

    /**
     * Возвращает флаг 'дороже'.
     */
    val expensive = MutableLiveData(_expensive)

    /**
     * Возвращает флаг 'в наличии'.
     */
    val stock = MutableLiveData(_stock)

    /**
     * Возвращает флаг 'со скидкой'.
     */
    val discount = MutableLiveData(_discount)

    /**
     * Возвращает флаг, что произошли изменения относительно предыдущего состояния (Используется для изменения состояния кнопки).
     */
    val isChanged = MediatorLiveData(false).apply {
        fun checkChanged() {
            value =
                popular.value!! != _popular || cheaper.value != _cheaper || expensive.value!! != _expensive || stock.value != _stock || discount.value!! != _discount

        }

        addSource(popular) {
            checkChanged()
        }
        addSource(cheaper) {
            checkChanged()
        }
        addSource(expensive) {
            checkChanged()
        }
        addSource(stock) {
            checkChanged()
        }
        addSource(discount) {
            checkChanged()
        }
    }

    /**
     * Возвращает выбранный тип сортировки.
     */
    val itemSelected = ScopedLiveData(R.string.product_sort_popular)

    /**
     * Возвращает флаг завершения редактирования.
     */
    val editingCompleted = SingleLiveEvent<Unit>()

    /**
     * Отменяет редактирование.
     */
    fun cancel() {
        popular.value = _popular
        cheaper.value = _cheaper
        expensive.value = _expensive
        stock.value = _stock
        discount.value = _discount
        editingCompleted.call()
    }

    /**
     * Применяет редактирование.
     */
    fun apply() {
        _popular = popular.value!!
        _cheaper = cheaper.value!!
        _expensive = expensive.value!!
        _stock = stock.value!!
        _discount = discount.value!!
        itemSelected.setValue(
            when(true) {
                popular.value!! -> R.string.product_sort_popular
                cheaper.value!! -> R.string.product_sort_cheaper
                expensive.value!! -> R.string.product_sort_expensive
                stock.value!! -> R.string.product_sort_stock
                discount.value!! -> R.string.product_sort_discount
                else -> throw IllegalArgumentException("Такая сортировка не предусмотрена")
            }
        )
        editingCompleted.call()
    }

}