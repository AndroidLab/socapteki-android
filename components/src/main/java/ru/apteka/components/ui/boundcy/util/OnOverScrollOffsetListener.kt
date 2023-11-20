package ru.apteka.components.ui.boundcy.util

interface OnOverScrollOffsetListener
{
    fun onOverScrollStart()

    fun onOverScrollOffset(mode: Int, offset: Int)

    fun onOverScrollRelease()

    fun onOverScrollEnd()
}