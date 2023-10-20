package ru.apteka.components.data.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay


/**
 * Автоматически скролит список.
 * @param recycler RecyclerView.
 */
tailrec suspend fun recyclerAutoScroll(
    recycler: RecyclerView
) {
    delay(3000)
    val layoutManager = recycler.layoutManager as LinearLayoutManager
    val firstPosition = layoutManager.findLastCompletelyVisibleItemPosition()
    if (firstPosition < recycler.adapter!!.itemCount-1) {
        recycler.smoothScrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() +1)
    } else {
        recycler.smoothScrollToPosition(0)
    }
    recyclerAutoScroll(recycler)
}