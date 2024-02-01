package ru.apteka.components.data.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CirclePagerIndicatorDecoration(
    private val verticalOffset: Int,
    private val colorActive: Int,
    private val colorInactive: Int
) : ItemDecoration() {
    private val mIndicatorStrokeWidth = 4.dp
    private val mIndicatorItemLength = 4.dp
    private val mIndicatorItemPadding = 8.dp
    private val mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private val mPaint = Paint()

    init {
        mPaint.strokeWidth = mIndicatorStrokeWidth.toFloat()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter!!.itemCount

        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        val indicatorPosY = parent.height.toFloat() - verticalOffset.dp
        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        val layoutManager = parent.layoutManager as LinearLayoutManager?
        val activePosition = layoutManager!!.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left
        val width = activeChild.width
        val right = activeChild.right

        val progress: Float = mInterpolator.getInterpolation(left * -1 / width.toFloat())
        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress)
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        mPaint.color = colorInactive

        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        highlightPosition: Int,
        progress: Float
    ) {
        mPaint.color = colorActive

        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        if (progress == 0f) {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2f, mPaint)
        } else {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            val partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress
            c.drawCircle(
                highlightStart + partialLength,
                indicatorPosY,
                mIndicatorItemLength / 2f,
                mPaint
            )
        }
    }

    /*override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }*/
}
