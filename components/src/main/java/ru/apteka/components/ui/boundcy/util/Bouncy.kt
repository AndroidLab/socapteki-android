package ru.apteka.components.ui.boundcy.util

import androidx.dynamicanimation.animation.SpringForce

@Suppress("unused")
object Bouncy
{
    //damping ratio
    const val DAMPING_RATIO_NO_BOUNCY = SpringForce.DAMPING_RATIO_NO_BOUNCY
    const val DAMPING_RATIO_LOW_BOUNCY = SpringForce.DAMPING_RATIO_LOW_BOUNCY
    const val DAMPING_RATIO_MEDIUM_BOUNCY = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
    const val DAMPING_RATIO_HIGH_BOUNCY = SpringForce.DAMPING_RATIO_HIGH_BOUNCY

    //stiffness
    const val STIFFNESS_VERY_LOW = SpringForce.STIFFNESS_VERY_LOW
    const val STIFFNESS_LOW = SpringForce.STIFFNESS_LOW
    const val STIFFNESS_MEDIUM = SpringForce.STIFFNESS_MEDIUM
    const val STIFFNESS_HIGH = SpringForce.STIFFNESS_HIGH

    //overscroll mode
    const val OVERSCROLL_ALL = 0
    const val OVERSCROLL_START = 1
    const val OVERSCROLL_END = 2
}