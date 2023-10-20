package ru.apteka.components.ui.delegate_adapter


import android.view.ViewGroup
import ru.apteka.components.data.utils.Skeleton
import ru.apteka.components.databinding.SceletonBinding


/**
 * Представляет адаптер для скелетона.
 */
class SkeletonAdapter(
    private val width: Int,
    private val height: Int
) :
    ViewBindingDelegateAdapter<Skeleton, SceletonBinding>(
        SceletonBinding::inflate
    ) {

    override fun SceletonBinding.onBind(item: Skeleton) {
        skeleton.layoutParams = ViewGroup.LayoutParams(width, height)
    }

    override fun isForViewType(item: Any) = item is Skeleton

    override fun Skeleton.getItemId() = Unit
}