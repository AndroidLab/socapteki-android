package ru.apteka.components.ui.boundcy.util

interface OnOverPullListener
{
    fun onOverPulledTop(deltaDistance: Float)

    fun onOverPulledBottom(deltaDistance: Float)

    fun onRelease()
}