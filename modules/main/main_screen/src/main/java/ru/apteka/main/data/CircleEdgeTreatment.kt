package ru.apteka.main.data

import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import kotlin.math.sqrt

/**
 * Представляет форму для нижней панели с вырезом для FAB.
 */
class CircleEdgeTreatment(private val radius: Float) : EdgeTreatment() {
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        val middle = length / 2.0f
        val verticalOffset = (1.0f - interpolation) * radius - radius / 3
        val verticalOffsetRatio = verticalOffset / radius
        if (verticalOffsetRatio >= 1.0f) {
            shapePath.lineTo(length, 0.0f)
        } else {
            val distanceBetweenCenters = radius
            val distanceBetweenCentersSquared = distanceBetweenCenters * distanceBetweenCenters
            val distanceY = verticalOffset
            val distanceX =
                sqrt((distanceBetweenCentersSquared - distanceY * distanceY).toDouble()).toFloat()
            val leftRoundedCornerCircleX = middle - distanceX
            shapePath.lineTo(leftRoundedCornerCircleX, 0.0f)
            shapePath.addArc(
                middle - radius,
                -radius - verticalOffset,
                middle + radius,
                radius - verticalOffset,
                180.0f,
                160.0f
            )
            shapePath.lineTo(length, 0.0f)
        }
    }
}