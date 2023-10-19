package ru.apteka.components.data.utils

import androidx.recyclerview.widget.DiffUtil
import ru.apteka.components.ui.composite_delegate_adapter.AdaptersState


class DiffUtilCallback(
    private val oldState: ru.apteka.components.ui.composite_delegate_adapter.AdaptersState,
    private val newState: ru.apteka.components.ui.composite_delegate_adapter.AdaptersState
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldState.data.size
    override fun getNewListSize(): Int = newState.data.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldState.itemId(oldItemPosition) == newState.itemId(newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldState.itemContent(oldItemPosition) == newState.itemContent(newItemPosition)

    private fun ru.apteka.components.ui.composite_delegate_adapter.AdaptersState.itemId(position: Int): Any =
        getAdapterByItemPosition(position).itemId(data[position])

    private fun ru.apteka.components.ui.composite_delegate_adapter.AdaptersState.itemContent(position: Int): Any =
        getAdapterByItemPosition(position).itemContent(data[position])
}