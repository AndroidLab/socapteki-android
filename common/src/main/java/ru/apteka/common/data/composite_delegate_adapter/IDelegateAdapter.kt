package ru.apteka.common.data.composite_delegate_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Описывает методы взаимодействия с адаптером.
 */
interface IDelegateAdapter {
    // Те же методы rv.adapter для делегирования
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    fun onBindViewHolder(holder: ViewHolder, items: List<Any>, position: Int)

    fun onRecycled(holder: ViewHolder)

    /**
     * Чтобы знать, что текущий адаптер может работать с элементом в положении
     * Возвращает флаг, что т
     * @param items
     * @param position
     */
    fun isForViewType(items: List<Any>, position: Int): Boolean

    /**
     * [DiffUtilCallback] использует, чтобы знать, что два объекта одинаковы.
     * @param item Объект для отображения.
     */
    fun itemId(item: Any): Any

    /**
     * [DiffUtilCallback] использует, чтобы знать, что два объекта одинаковып по контенту.
     * @param item Объект для отображения.
     */
    fun itemContent(item: Any): Any

    fun onAttachedToWindow(holder: ViewHolder)

    fun onDetachedFromWindow(holder: ViewHolder)
}