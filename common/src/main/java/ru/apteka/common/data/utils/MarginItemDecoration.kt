package ru.apteka.common.data.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Представляет декоратор, который добавляет отступы для 4 сторон отдельно.
 * @param topSize Верхнее значение.
 * @param rightSize Правое значение.
 * @param bottomSize Нижнее значение.
 * @param leftSize Левое значение.
 */
class MarginItemDecoration(
    private val topSize: Int,
    private val rightSize: Int,
    private val bottomSize: Int,
    private val leftSize: Int,
) : RecyclerView.ItemDecoration() {
    /**
     * Создает декоратор, который добавляет отступы по горизонтали и по вертикали.
     * @param horizontal Горизонтальное значение.
     * @param vertical Вертикальное значение.
     */
    constructor(horizontal: Int, vertical: Int) : this(vertical, horizontal, vertical, horizontal)

    /**
     * Создает декоратор, который добавляет одинаковый отступ для всех сторон.
     * @param all Значение для всех сторон.
     */
    constructor(all: Int) : this(all, all, all, all)

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topSize
            }
            left = leftSize
            right = rightSize
            bottom = bottomSize
        }
    }
}