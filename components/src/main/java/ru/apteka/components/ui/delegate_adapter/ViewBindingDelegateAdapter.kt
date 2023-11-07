package ru.apteka.components.ui.delegate_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * Представляет адаптер для биндинга.
 */
abstract class ViewBindingDelegateAdapter<T : Any, V : ViewDataBinding>(
    private val viewBindingInflater: (LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> V
) : IDelegateAdapter {

    /**
     * Возвращает или устанавливает класс биндинга.
     */
    var binding: V? = null
        private set

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewBindingHolder(
            viewBindingInflater.invoke(layoutInflater, parent, false).also {
                binding = it
            }
        )
    }

    open fun V.onCreated() {}

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, items: List<Any>, position: Int) {
        holder as ViewBindingHolder<V>
        holder.viewDataBinding.onBind(items[position] as T, position, position == 0, position == items.size-1)
    }

    abstract fun V.onBind(item: T, position: Int, isFirst: Boolean, isLast: Boolean)

    override fun onRecycled(holder: RecyclerView.ViewHolder) {
        (holder as ViewBindingHolder<V>).viewDataBinding.onRecycled()
    }

    open fun V.onRecycled() {}

    abstract fun isForViewType(item: Any): Boolean

    abstract fun T.getItemId(): Any

    override fun itemContent(item: Any): Any = item

    @Suppress("UNCHECKED_CAST")
    final override fun itemId(item: Any): Any = (item as T).getItemId()

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return isForViewType(items[position])
    }

    @Suppress("UNCHECKED_CAST")
    final override fun onAttachedToWindow(holder: RecyclerView.ViewHolder)
        = (holder as ViewBindingHolder<V>).viewDataBinding.onAttachedToWindow()

    @Suppress("UNCHECKED_CAST")
    final override fun onDetachedFromWindow(holder: RecyclerView.ViewHolder)
        = (holder as ViewBindingHolder<V>).viewDataBinding.onDetachedFromWindow()

    open fun V.onAttachedToWindow() {}
    open fun V.onDetachedFromWindow() {}

    private class ViewBindingHolder<V : ViewDataBinding>(
        val viewDataBinding: V
    ) : RecyclerView.ViewHolder(viewDataBinding.root)
}
