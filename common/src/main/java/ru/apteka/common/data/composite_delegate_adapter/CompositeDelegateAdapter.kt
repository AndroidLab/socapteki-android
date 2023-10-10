package ru.apteka.common.data.composite_delegate_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Представляет составной адаптер для [RecyclerView].
 * @param adapters Список адаптеров.
 */
open class CompositeDelegateAdapter(vararg adapters: IDelegateAdapter) : RecyclerView.Adapter<ViewHolder>() {

    // Контракт таков: позиция адаптеров используется в качестве viewType.
    protected open var adapterState = AdaptersState(adapters.toList())

    override fun getItemViewType(itemPosition: Int): Int = adapterState.getAdapterPosition(itemPosition)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        adapterState.getAdapter(viewType).onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        adapterState.getAdapter(getItemViewType(position))
            .onBindViewHolder(holder, adapterState.data, position)

    override fun onViewRecycled(holder: ViewHolder) =
        adapterState.getAdapter(holder.itemViewType).onRecycled(holder)

    open fun swapData(data: List<Any>) {
        val newAdapterState = adapterState.copy(data = data)
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(adapterState, newAdapterState)
        )
        adapterState = newAdapterState
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        adapterState.getAdapter(holder.itemViewType).onAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        adapterState.getAdapter(holder.itemViewType).onDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int = adapterState.data.size
}